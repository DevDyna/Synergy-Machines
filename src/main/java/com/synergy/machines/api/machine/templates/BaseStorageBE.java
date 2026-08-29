package com.synergy.machines.api.machine.templates;

import java.util.List;
import java.util.function.Function;

import com.devdyna.cakesticklib.api.aspect.logic.ItemStorageBlock;
import com.devdyna.cakesticklib.api.aspect.logic.MachineItemAutomation;
import com.devdyna.cakesticklib.api.aspect.logic.MenuProvider;
import com.devdyna.cakesticklib.setup.registry.LibHandlers;
import com.synergy.machines.api.machine.TypedFluidStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.resource.Resource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public abstract class BaseStorageBE extends BaseRecipeBE
        implements MachineItemAutomation, MenuProvider, ItemStorageBlock {

    public static final int INPUT_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

    public static final int FLUID_DATA_SIZE = 3;

    protected Function<TypedFluidStorage, List<Integer>> FLUID_DATA = f -> List.of(
            f.getFluidStorage().getAmountAsInt(0),
            f.getTankCapacity(),
            BuiltInRegistries.FLUID.getId(f.getAsStack(0).getFluid())

    );

    public BaseStorageBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public int getSlots() {
        return getMachineSlots();
    }

    @Override
    public ItemStacksResourceHandler getItemStorage() {
        return getData(LibHandlers.ITEM_STORAGE);
    }

    public ItemStack getInput() {
        return getStackInSlot(INPUT_SLOT);
    }

    public ItemStack getOutput() {
        return getStackInSlot(OUTPUT_SLOT);
    }

    @Override
    public List<Integer> getInputSlotIndex() {
        return List.of(INPUT_SLOT);
    }

    @Override
    public List<Integer> getOutputSlotIndex() {
        return List.of(OUTPUT_SLOT);
    }

    @Override
    public ItemStacksResourceHandler getAutomationItemStorage() {
        return new ItemStacksResourceHandler(getMachineSlots()) {

            @Override
            public boolean isValid(int i, ItemResource r) {
                return getInputSlotIndex().contains(i) && getItemStorage().isValid(i, r);
            }

            @Override
            public int extract(ItemResource r, int a, TransactionContext t) {
                if (r.isEmpty() || a <= 0)
                    return 0;

                int extracted = 0;

                for (int i : getOutputSlotIndex()) {
                    extracted += getItemStorage().extract(i, r, a - extracted, t);

                    if (extracted >= a)
                        break;
                }

                return extracted;
            }

            @Override
            public int insert(ItemResource r, int a, TransactionContext t) {

                if (r.isEmpty() || a <= 0)
                    return 0;

                int inserted = 0;

                for (int i : getInputSlotIndex()) {
                    inserted += getItemStorage().insert(i, r, a - inserted, t);

                    if (inserted >= a)
                        break;
                }

                return inserted;
            }

            @Override
            public int extract(int i, ItemResource r, int a, TransactionContext t) {
                if (getOutputSlotIndex().contains(i))
                    return getItemStorage().extract(i, r, a, t);
                return 0;
            }

            @Override
            public int insert(int i, ItemResource r, int a, TransactionContext t) {
                if (getInputSlotIndex().contains(i))
                    return getItemStorage().insert(i, r, a, t);
                return 0;
            }

        };
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return MachineItemAutomation.super.getStackInSlot(i);
    }

    @Override
    public void set(int i, ItemResource resource, int amount) {
        getAutomationItemStorage().set(i, resource, amount);
    }

    @Override
    public Component getContainerName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    /**
     * Update a resource
     * 
     * @param consume define if the update will increase or decrease the value
     */
    protected <RESOURCE extends Resource, STACK> void updateResource(RESOURCE resource, int slot, int amount,
            boolean consume) {

        StacksResourceHandler<STACK, RESOURCE> storage = null;

        if (resource instanceof ItemResource)
            storage = (StacksResourceHandler<STACK, RESOURCE>) getItemStorage();

        if (resource instanceof FluidResource && this instanceof TypedFluidStorage f)
            storage = (StacksResourceHandler<STACK, RESOURCE>) f.getFluidStorage();

        if (storage == null || resource.isEmpty())
            return;

        try (Transaction tx = Transaction.openRoot()) {
            if (consume)
                storage.extract(resource, amount, tx);
            else
                storage.insert(slot, resource, amount, tx);
            tx.commit();
        }
    }

    /**
     * Return true when output slot can accept {@code recipeSlot}
     * <br/>
     * <br/>
     * Use this ONLY for output slots
     */
    public boolean checkSlot(ItemStack slot, ItemStack recipeSlot) {

        if (slot.isEmpty())
            return true;

        if (!ItemStack.isSameItemSameComponents(slot, recipeSlot))
            return false;

        if (slot.getMaxStackSize() < slot.getCount() + recipeSlot.getCount())
            return false;

        return true;
    }

    /**
     * Return true when fluid tank can accept {@code recipeSlot}
     * <br/>
     * <br/>
     * Use this ONLY for output tanks
     */
    public boolean checkTank(FluidStack slot, FluidStack recipeSlot, int maxTank) {

        if (slot.isEmpty() || slot.getAmount() <= 0)
            return true;

        if (!FluidStack.isSameFluidSameComponents(slot, recipeSlot))
            return false;

        if (maxTank < slot.getAmount() + recipeSlot.getAmount())
            return false;

        return true;
    }

}
