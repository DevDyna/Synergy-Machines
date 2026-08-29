
package com.synergy.machines.api.machine;

import com.devdyna.cakesticklib.api.utils.UpgradeComponents.UpgradeType;
import com.synergy.machines.api.machine.templates.BaseUpgradableBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public abstract class BaseMachineBE extends BaseUpgradableBE {

    public BaseMachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ContainerData networkData = new ContainerData() {

        @Override
        public int getCount() {
            return ENERGY_DATA_SIZE + PROGRESS_DATA_SIZE
                    + (BaseMachineBE.this instanceof TypedFluidStorage
                            ? FLUID_DATA_SIZE
                            : 0);
        }

        @Override
        public int get(int i) {
            return switch (i) {
                case BaseMachineMenu.PROGRESS_INDEX -> getProgress();
                case BaseMachineMenu.MAX_PROGRESS_INDEX -> getMaxProgress();
                case BaseMachineMenu.ENERGY_INDEX -> getEnergyStorage().getAmountAsInt();
                case BaseMachineMenu.MAX_ENERGY_INDEX -> getMaxEnergy();
                case BaseMachineMenu.ENERGY_USAGE -> getEnergyUsage();
                case BaseMachineMenu.FLUID_INDEX,
                        BaseMachineMenu.MAX_FLUID_INDEX,
                        BaseMachineMenu.ID_FLUID_INDEX ->
                    (BaseMachineBE.this instanceof TypedFluidStorage fluid) ? switch (i) {
                        case BaseMachineMenu.FLUID_INDEX -> fluid.getFluidStorage().getAmountAsInt(0);
                        case BaseMachineMenu.MAX_FLUID_INDEX -> fluid.getTankCapacity();
                        case BaseMachineMenu.ID_FLUID_INDEX -> TypedFluidStorage.getFluidToID(fluid.getAsStack(0));
                        default -> 0;
                    } : 0;

                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {

        }

    };

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    @Override
    public void update(boolean v) {

        if (level == null || isRemoved())
            return;

        if (v != getBlockState().getValue(BaseMachineBlock.ENABLED))
            level.setBlockAndUpdate(getBlockPos(),
                    getBlockState().setValue(BaseMachineBlock.ENABLED, v));

    }

    // TODO IMP : move to BaseUpgradableBE
    // TODO API : remove and add fluids
    @Override
    public void tryToEject(
            ItemStacksResourceHandler storage,
            boolean dropInWorld,
            int... slots) {

        if (storage == null
                || slots == null
                || slots.length == 0) {

            return;
        }

        if (getLevel() == null
                || getLevel().isClientSide()) {

            return;
        }

        var ejectValues = getValues(UpgradeType.EJECT);

        if (ejectValues == null
                || ejectValues.isEmpty()) {

            return;
        }

        Direction facing = (Direction) ejectValues.getFirst();

        if (facing == null)
            return;

        BlockPos outputPos = getBlockPos().relative(facing);

        var output = getLevel().getCapability(
                Capabilities.Item.BLOCK,
                outputPos,
                facing.getOpposite());

        for (int slot : slots) {

            if (slot < 0
                    || slot >= storage.size()) {

                continue;
            }

            var resource = storage.getResource(slot);

            if (resource.isEmpty())
                continue;

            int amount = storage.getAmountAsInt(slot);

            if (amount <= 0)
                continue;

            int inserted = 0;

            if (output != null) {

                try (Transaction tx = Transaction.openRoot()) {

                    inserted = output.insert(
                            resource,
                            amount,
                            tx);

                    if (inserted > 0)
                        tx.commit();
                }
            }

            if (inserted < amount
                    && dropInWorld) {

                int remaining = amount - inserted;

                Containers.dropItemStack(
                        getLevel(),
                        outputPos.getX() + 0.5D,
                        outputPos.getY() + 0.5D,
                        outputPos.getZ() + 0.5D,
                        resource.toStack(remaining));

                inserted = amount;
            }

            if (inserted > 0) {

                try (Transaction tx = Transaction.openRoot()) {

                    int extracted = storage.extract(
                            slot,
                            resource,
                            inserted,
                            tx);

                    if (extracted > 0)
                        tx.commit();
                }
            }
        }
    }

    @Override
    public void result() {
        // TODO REWORK
        if (!getValues(UpgradeType.EJECT).isEmpty())
            this.tryToEject(
                    getItemStorage(),
                    false,
                    getOutputSlotIndex()
                            .stream()
                            .mapToInt(Integer::intValue)
                            .toArray());
    }

}
