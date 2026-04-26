package com.synergy.machines.api.machine;

import java.util.List;
import java.util.Optional;

import com.devdyna.cakesticklib.api.aspect.logic.*;
import com.devdyna.cakesticklib.api.aspect.templates.MachineBE;
import com.devdyna.cakesticklib.api.recipe.recipeType.BaseRecipeType;
import com.devdyna.cakesticklib.setup.registry.zLibrary.zComponents;
import com.devdyna.cakesticklib.setup.registry.zLibrary.zHandlers;
import com.mojang.logging.LogUtils;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.RecipeRegister;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStackResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/**
 * <b>INDUSTRIAL MACHINE BASE BE</b>
 * <br/>
 * <br/>
 * Base BE of all Industrial Machine
 * <br/>
 * <br/>
 * It can handle by default Items and Energy Capability
 * <br/>
 * <br/>
 * Also it contain multiple ready-to-use methods useful on recipe handling
 * <br/>
 * <br/>
 * |-----------------------------------------------------------------|<br/>
 * <br/>
 * The Machine much be registred using
 * <code>com.devdyna.synergy.api.registers.MachineType</code>
 * <br/>
 * <br/>
 * credit: @DevDyna
 */
@SuppressWarnings("null")
public abstract class BaseMachineBE extends MachineBE implements MachineItemAutomation, EnergyBlock , UpgradeInstallable{

    public static final int DEFAULT_FE_COST = 500;
    public static final int DEFAULT_TICK_DURATION = 100;

    protected int progress = 0;
    protected int maxProgress;
    protected int energy = 0;
    protected int maxEnergy = 0;
    protected int fluid_amount = 0;
    protected int maxFluid = 0;
    protected int fe_usage = 0;

    public static final int PROGRESS_INDEX = 0;
    public static final int MAX_PROGRESS_INDEX = 1;
    public static final int ENERGY_INDEX = 2;
    public static final int MAX_ENERGY_INDEX = 3;
    public static final int ENERGY_USAGE = 4;
    public static final int FLUID_INDEX = 5;
    public static final int MAX_FLUID_INDEX = 6;

    public static final int SLOT_UPGRADE_1 = 0;
    public static final int SLOT_UPGRADE_2 = 1;
    public static final int SLOT_UPGRADE_3 = 2;
    public static final int SLOT_UPGRADE_4 = 3;

    public static final int MAX_UPGRADE_SLOTS = 4;

    /**
     * To add new slots you should use ExtraMachineSlots interface!
     * <br/>
     * <br/>
     * The first slot Index must be 6 -> ?
     */
    public static final int INPUT_SLOT = 4;
    /**
     * To add new slots you should use ExtraMachineSlots interface!
     * <br/>
     * <br/>
     * The first slot Index must be 6 -> ?
     */
    public static final int OUTPUT_SLOT = 5;

    @Deprecated
    public static final int BASE_SLOT_IO = 2;

    @Deprecated
    public static final int TOTAL_BASE_SLOT_IO = MAX_UPGRADE_SLOTS + BASE_SLOT_IO;

    protected boolean progress_cancel;

    /**
     * Server side data sent to client side render
     */
    public ContainerData networkData;

    public static int check(Level l, int t, int f) {
        return (l != null && !l.isClientSide()) ? t : f;
    }

    public BaseMachineBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);

        this.networkData = new ContainerData() {
            @Override
            public int get(int i) {

                if (BaseMachineBE.this instanceof FluidTankStorage fluid)
                    switch (i) {
                        case FLUID_INDEX:
                            return check(level, fluid.getFluidStorage().getAmountAsInt(0), fluid_amount);
                        case MAX_FLUID_INDEX:
                            return check(level, fluid.getTankCapacity(), maxFluid);

                    }

                return switch (i) {
                    case PROGRESS_INDEX -> getProgress();
                    case MAX_PROGRESS_INDEX -> getMaxProgress();
                    case ENERGY_INDEX -> check(level, energyStorage.getAmountAsInt(), energy);
                    case MAX_ENERGY_INDEX -> check(level, energyStorage.getCapacityAsInt(), maxEnergy);
                    case ENERGY_USAGE -> getEnergyUsage();
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
            }

            @Override
            public int getCount() {
                return 5 + ((BaseMachineBE.this instanceof FluidTankStorage) ? 2 : 0);
            }
        };
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
    public int getSlots() {
        return getMachineSlots();
    }

    @Override
    public ItemStacksResourceHandler getAutomationItemStorage() {
        return new ItemStacksResourceHandler(getMachineSlots());
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putInt("progress", progress);
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        if (input.getInt("progress").isPresent())
            progress = input.getInt("progress").get();
        super.loadAdditional(input);
    }

    @Override
    public EnergyHandler getEnergyStorage() {
        return getData(zHandlers.ENERGY_STORAGE);
    }

    @Override
    public int getMaxEnergy() {
        return 16_000
        // calculateFECapacity(
        // Common.MACHINE_MAX_FE.get()
        // )
        ;
    }

    @Override
    public ContainerData getContainerData() {
        return networkData;
    }

    protected void resetProgress() {
        progress_cancel = true;
        if (progress > 0)
            progress--;
        if (progress == 0)
            progress_cancel = false;

        update(false);

        setChanged();
    }

    /**
     * This must be used the return value as return of initProgress();
     */
    protected boolean cancel() {
        resetProgress();
        return false;
    }

    private boolean toggle = false;

    protected void update(boolean v) {
        if (v != toggle) {
            toggle = !toggle;
            level.setBlockAndUpdate(getBlockPos(),
                    getBlockState().setValue(BaseMachineBlock.ENABLED, v));
        }

    }

    protected void tick() {
        try {
            tickBoth();
            if (level.isClientSide())
                tickClient();
            else
                tickServer();

        } catch (Exception e) {
            // catch potential crashes
            if (level.getBlockEntity(getBlockPos()) instanceof BaseMachineBE) {
                LogUtils.getLogger().error(
                        "BlockEntity at " + getBlockPos() + " has invalid data -> Broken to prevent crash");
                LogUtils.getLogger().error("Contact Mod Author and report this as BUG");
                LogUtils.getLogger().error(e.getMessage());
                e.printStackTrace();
                level.removeBlockEntity(getBlockPos());
                level.destroyBlock(getBlockPos(), true);
            }
        }

    }

    public void tickBoth() {
    }

    public void tickClient() {
    }

    @Deprecated
    public void tickServer() {

        if (!initProgress())
            return;

        if (progress_cancel)
            return;
        else
            this.progress++;

        // if the recipe is modified by player

        if (this.progress < this.maxProgress) {
            setChanged();
            return;
        }

        endProgress();

        progress = 0;

        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    /**
     * Return false to cancel
     */
    public boolean initProgress() {

        return false;
    }

    public void endProgress() {

    }

    public boolean isCrafting() {
        return progress > 0 && !progress_cancel;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public ItemStack getInput() {
        return getStackInSlot(INPUT_SLOT);
    }

    public ItemStack getOutput() {
        return getStackInSlot(OUTPUT_SLOT);
    }

    /**
     * Return <code>true</code> when success
     * <br/>
     * <br/>
     * Use this ONLY to check output slots
     */
    public boolean checkSlot(ItemStack slot, ItemStack recipeSlot) {
        if (!slot.isEmpty()) {
            // same item
            if (ItemStack.isSameItemSameComponents(slot, recipeSlot)) {
                // count valid
                if (slot.getMaxStackSize() < slot.getCount() + recipeSlot.getCount()) {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Return <code>true</code> when success
     * <br/>
     * <br/>
     * Use this ONLY to check output slots
     */
    public boolean checkTank(FluidStack slot, FluidStack recipeSlot, int max_fluid_tank) {
        if (!slot.isEmpty()) {
            // same item
            if (FluidStack.isSameFluidSameComponents(slot, recipeSlot)) {
                // count valid
                if (max_fluid_tank < slot.getAmount() + recipeSlot.getAmount()) {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    public void updateOutputSlot(ItemStack stack, ItemStack slotStack, int slotIndex) {
        if (stack.isEmpty())
            setStackInSlot(slotIndex, slotStack);
        else if (ItemStack.isSameItemSameComponents(stack, slotStack))
            stack.grow(slotStack.getCount());
    }

    public List<Integer> getUpgradeSlots() {
        return List.of(SLOT_UPGRADE_1, SLOT_UPGRADE_2, SLOT_UPGRADE_3, SLOT_UPGRADE_4).stream()
                .filter(i -> i < getSlots())
                .toList();
    }

    // public List<ItemStack> getUpgradeInstalled() {
    //     return getUpgradeIndexs().stream()
    //             .map(this::getStackInSlot)
    //             .filter(i -> i.getItem() instanceof IndustrialUpgrade)
    //             .filter(i -> i.get(zComponents.UPGRADE_COMPONENTS) != null)
    //             .toList();
    // }

    // public List<Integer> getValues(UpgradeType type) {
    //     List<ItemStack> upgrades = getUpgradeInstalled().stream()
    //             .filter(i -> UpgradeComponents.has(i, type))
    //             .toList();

    //     List<Integer> validSlots = new ArrayList<>();
    //     int maxRoll = getTypeLimiter(type);

    //     for (int i = 0; i < upgrades.size() && validSlots.size() < maxRoll; i++)
    //         for (int j = 0; j < upgrades.get(i).getCount() && validSlots.size() < maxRoll; j++)
    //             validSlots.add(UpgradeComponents.get(upgrades.get(i), type));

    //     return validSlots;
    // }

    // public int getTypeLimiter(UpgradeType type) {
    //     if (type.equals(UpgradeType.SPEED))
    //         return Common.MACHINE_MAX_SPEED_UPGRADES_TYPE.get();
    //     if (type.equals(UpgradeType.ENERGY))
    //         return Common.MACHINE_MAX_ENERGY_EFFICIENCY_UPGRADES_TYPE.get();
    //     if (type.equals(UpgradeType.LUCK))
    //         return Common.MACHINE_MAX_LUCK_UPGRADES_TYPE.get();
    //     if (type.equals(UpgradeType.FLUID))
    //         return Common.MACHINE_MAX_FLUID_UPGRADES_TYPE.get();
    //     return Integer.MAX_VALUE;
    // }

    @Override
    public ItemStackResourceHandler getUpgradeItemStorage() {
        return null;//TODO
    }

    // public int calculateMaxProgress(int base) {
    //     var upgrades = getValues(UpgradeType.SPEED);
    //     float sum = upgrades == null ? 0 : upgrades.stream().mapToInt(Integer::intValue).sum();
    //     return Common.MACHINE_MAX_SPEED_UPGRADES_TYPE.get() == 0 ? base
    //             : Math.max(Common.MACHINE_MINIMAL_TICK_DELAY.get(),
    //                     (int) (base - (base * sum / 100)));
    // }

    // private int calculateFEUsage(int base) {
    //     var upgrades = getValues(UpgradeType.ENERGY);
    //     float sum = upgrades == null ? 0 : upgrades.stream().mapToInt(Integer::intValue).sum();
    //     return Common.MACHINE_MAX_ENERGY_EFFICIENCY_UPGRADES_TYPE.get() == 0 ? base
    //             : Math.max(Common.MACHINE_MINIMAL_FE_COST.get(),
    //                     (int) (base + (base * sum / 100)));
    // }

    // public int calculateMBUsage(int base) {
    //     var upgrades = getValues(UpgradeType.FLUID);
    //     float sum = upgrades == null ? 0 : upgrades.stream().mapToInt(Integer::intValue).sum();
    //     return Common.MACHINE_MAX_FLUID_UPGRADES_TYPE.get() == 0 ? base
    //             : Math.max(Common.MACHINE_MINIMAL_FLUID_COST.get(),
    //                     (int) (base + (base * sum / 100f)));
    // }

    // public boolean calculateSecondarySuccess(float base) {
    //     var upgrades = getValues(UpgradeType.LUCK);
    //     float sum = upgrades == null ? 0 : upgrades.stream().mapToInt(Integer::intValue).sum();
    //     return Common.MACHINE_MAX_LUCK_UPGRADES_TYPE.get() == 0 ? false
    //             : level.getRandom().nextFloat() < Math.min(
    //                     Common.MACHINE_MAXIMAL_LUCK.get(),
    //                     (base + (sum / 100)));
    // }

    /**
     * Return <code>true</code> when success
     */
    public boolean calculateAndConsumeFE(int min) {
        var base = calculateFEUsage(min);
        this.fe_usage = base;
        if (energyStorage.getAmountAsInt() >= base && !progress_cancel) {
            try (var tx = Transaction.openRoot()) {
                energyStorage.extract(base, tx);
                tx.commit();
            }
            return true;
        } else
            return false;
    }

    public boolean tryAddUpgrade(ItemStack item) {
        var upgrade = item.copy();
        upgrade.setCount(1);

        if (!upgrade.has(zComponents.UPGRADE_COMPONENTS))
            return false;

        for (int index = 0; index < MAX_UPGRADE_SLOTS; index++) {
            var slot = getStackInSlot(index);

            if (slot.isEmpty()) {

                setStackInSlot(index, upgrade);
                return true;
            }

            if (ItemStack.isSameItemSameComponents(upgrade, slot) && slot.getCount() < 4) {
                slot.grow(1);
                return true;
            }
        }

        return false;
    }

    public int getEnergyUsage() {
        return fe_usage;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return MachineItemAutomation.super.getStackInSlot(index);
    }

    @Override
    public void set(int index, ItemResource resource, int amount) {
        getAutomationItemStorage().set(index, resource, amount);
    }

    // TODO move to another class
    public static <T extends BaseMachineRecipeType<I>, I extends RecipeInput> Optional<RecipeHolder<T>> getRecipes(
            Level level, MachineType<?, ?, ?, T> m, I input) {
        return level.getServer().getRecipeManager().getRecipeFor(m.recipe().getType(), input, level);
    }

    public static <T extends BaseMachineRecipeType<I>, I extends RecipeInput> T getUnsafeRecipes(Level level,
            MachineType<?, ?, ?, T> m, I input) {
        return level.getServer().getRecipeManager().getRecipeFor(m.recipe().getType(), input, level).get().value();
    }

    public static <T extends BaseRecipeType<I>, I extends RecipeInput> T getUnsafeRecipes(Level level,
            RecipeRegister<T> m, I input) {
        return level.getServer().getRecipeManager().getRecipeFor(m.getType(), input, level).get().value();
    }

}
