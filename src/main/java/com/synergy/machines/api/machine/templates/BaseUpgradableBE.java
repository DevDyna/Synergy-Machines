package com.synergy.machines.api.machine.templates;

import java.util.List;

import com.devdyna.cakesticklib.api.aspect.logic.ResourceRestricted;
import com.devdyna.cakesticklib.api.upgrades.UpgradeInstallable;
import com.devdyna.cakesticklib.api.upgrades.modifiers.ModifierUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public abstract class BaseUpgradableBE extends BaseStorageBE implements UpgradeInstallable {

    public static final int SLOT_UPGRADE_1 = 0;
    public static final int SLOT_UPGRADE_2 = 1;
    public static final int SLOT_UPGRADE_3 = 2;
    public static final int SLOT_UPGRADE_4 = 3;

    @Deprecated
    public final int UPGRADES_SIZE = getUpgradeSlots().size();

    public BaseUpgradableBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public ItemStacksResourceHandler getUpgradeItemStorage() {
        return getItemStorage();
    }

    public List<Integer> getUpgradeSlots() {
        return List.of(SLOT_UPGRADE_1, SLOT_UPGRADE_2, SLOT_UPGRADE_3, SLOT_UPGRADE_4);
    }

    /**
     * return {@code true} when succed
     */
    public boolean calculateAndConsumeFE(int min) {

        var fe = calculateFEUsage(min);

        setEnergyUsed(fe);

        if (getEnergyStorage().getAmountAsInt() >= fe && !isRecipeCancelled())
            try (var tx = Transaction.openRoot()) {
                getEnergyStorage().extract(fe, tx);
                tx.commit();
                return true;
            }

        return false;
    }

    public boolean tryAddUpgrade(ItemStack item) {

        // if (level.isClientSide())
        //     return false;

        var upgrade = item.copy();
        upgrade.setCount(1);

        if (!ModifierUtils.itemValid(item))
            return false;

        for (var i : getUpgradeSlots()) {
            if (getItemStorage().getAmountAsInt(i) < 4)
                if (insertItem(i, upgrade).isEmpty()) {
                    // item.shrink(1);
                    return true;
                }
        }

        return false;
    }

    @Override
    protected void ejection() {
        tryToEject(getAutomationItemStorage(), getOutputSlotIndex());

        if (this instanceof ResourceRestricted.Fluid f)
            tryToEject(f.getFluidStorage(), f.getOutputTankIndex());
    }

}
