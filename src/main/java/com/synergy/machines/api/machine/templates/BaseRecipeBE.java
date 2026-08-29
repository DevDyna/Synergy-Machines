package com.synergy.machines.api.machine.templates;

import java.util.List;
import java.util.Optional;

import com.devdyna.cakesticklib.api.recipe.recipeType.BaseRecipeType;
import com.synergy.machines.api.MachineType;
import com.synergy.machines.api.RecipeRegister;
import com.synergy.machines.api.machine.BaseMachineBE;
import com.synergy.machines.api.machine.recipe.BaseMachineRecipeType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class BaseRecipeBE extends BaseEnergyBE {

    private int progress = 0;
    private int maxProgress;

    private boolean progress_cancel;

    public static final int PROGRESS_DATA_SIZE = 2;

    protected List<Integer> RECIPE_DATA = List.of(
            getProgress(),
            getMaxProgress()

    );

    public BaseRecipeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setMaxProgress(int v) {
        this.maxProgress = v;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public boolean isRecipeCancelled() {
        return progress_cancel;
    }

    public void enableProgress() {
        this.progress_cancel = false;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putInt("progress", progress);
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        input.getInt("progress").ifPresent(v -> progress = v);
        super.loadAdditional(input);
    }

    /**
     * Define initial conditions that this BE must respect to procede on crafting
     * <br/>
     * <br/>
     * It should contain :<br/>
     * <br/>
     * - <strong>Input</strong> validation <br/>
     * <br/>
     * - <strong>Recipe</strong> validation <br/>
     * <br/>
     * - <strong>Output Space</strong> validation <br/>
     * <br/>
     * - <strong>FE</strong> consumption<br/>
     * <br/>
     * <strong>Result values</strong> :
     * <br/>
     * <br/>
     * Return {@link BaseMachineBE#succed} to cancel
     * <br/>
     * <br/>
     * Return {@link BaseMachineBE#cancel} to cancel
     */
    public abstract boolean init();

    /**
     * Define result procedures after progress will complete
     * <br/>
     * <br/>
     * It should contain :<br/>
     * <br/>
     * - <strong>Input</strong> resource <strong>Update</strong><br/>
     * <br/>
     * - <strong>Output</strong> resource <strong>Update</strong>
     */
    public abstract void result();

    /**
     * This must be used the as return value of
     * {@link BaseMachineBE#init}
     */
    public boolean cancel() {
        progress_cancel = true;

        if (progress > 0)
            progress--;

        if (progress == 0)
            progress_cancel = false;

        update(false);
        setChanged();
        return false;
    }

    /**
     * This must be used the as return value of
     * {@link BaseMachineBE#init}
     */
    public boolean succed() {
        // TODO maybe pointless
        return true;
    }

    /**
     * Must define any Blockstate update
     */
    public abstract void update(boolean v);

    /**
     * Client only ticking
     */
    public void tickClient() {
    }

    /**
     * Client and server ticking
     */
    public void tickBoth() {
    }

    /**
     * Server only ticking
     * <br/>
     * <br/>
     * <strong>Do not use machine processing here unless super is called</strong>
     */
    public void tickServer() {

        if (!init())
            return;

        if (progress_cancel)
            return;

        this.progress++;

        if (this.progress < this.maxProgress) {
            setChanged();
            return;
        }

        result();

        progress = 0;
    }

    public static <RECIPE extends BaseMachineRecipeType<INPUT>, INPUT extends RecipeInput> Optional<RecipeHolder<RECIPE>> getRecipes(
            Level level, MachineType<?, ?, ?, RECIPE> machine, INPUT input) {
        return (level == null || level.getServer() == null)
                ? Optional.empty()
                : level.getServer().getRecipeManager().getRecipeFor(machine.recipe().getType(), input, level);
    }

    public static <RECIPE extends BaseMachineRecipeType<INPUT>, INPUT extends RecipeInput> RECIPE getUnsafeRecipes(
            Level level, MachineType<?, ?, ?, RECIPE> machine, INPUT input) {
        return getUnsafeRecipes(level, machine.recipe().getType(), input);
    }

    public static <RECIPE extends Recipe<INPUT>, INPUT extends RecipeInput> RECIPE getUnsafeRecipes(
            Level level, RecipeType<RECIPE> recipe, INPUT input) {
        return level.getServer().getRecipeManager().getRecipeFor(recipe, input, level).get().value();
    }

    public static <RECIPE extends BaseRecipeType<INPUT>, INPUT extends RecipeInput> RECIPE getUnsafeRecipes(Level level,
            RecipeRegister<RECIPE> machine, INPUT input) {
        return getUnsafeRecipes(level, machine.getType(), input);
    }

}
