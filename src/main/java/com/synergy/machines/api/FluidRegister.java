package com.synergy.machines.api;

import java.util.function.ToIntFunction;

import javax.annotation.Nullable;
import java.awt.Color;

import com.devdyna.cakesticklib.api.utils.ColorUtils;
import com.synergy.machines.init.types.*;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;

//TODO IMP : rework to remove not anymore used stuff

/**
 * Utility class to create fluids
 */
public class FluidRegister {

    private String id;

    private DeferredHolder<Fluid, BaseFlowingFluid.Source> fluidsource;
    private DeferredHolder<Fluid, FlowingFluid> fluidflowing;
    private DeferredHolder<Item, BucketItem> itemBucket;
    private DeferredHolder<Block, LiquidBlock> block;
    private BaseFlowingFluid.Properties prop;
    private DeferredHolder<FluidType, ?> type;

    private int lightLevel;
    private ToIntFunction<BlockState> dynLightLevel;
    private int viscosity;
    private boolean canDrown;
    private boolean canSwim;
    private boolean canPushEntity;
    private boolean canConvertToSource;
    private int color;

    public FluidRegister(String id, int color) {
        this(id, color, false);
    }

    public FluidRegister(String id, int color, boolean noBucket) {

        this.id = id;
        this.color = color;
        this.viscosity = 1000;// approx water
        this.lightLevel = 0;
        this.dynLightLevel = a -> lightLevel;
        this.canDrown = false;
        this.canSwim = false;
        this.canPushEntity = false;
        this.canConvertToSource = false;

        this.type = zFluids.zFluidTypes.register(
                id + "_type",
                p -> new FluidType(FluidType.Properties.create()
                        .lightLevel(lightLevel)
                        .viscosity(viscosity)
                        .canDrown(canDrown)
                        .canSwim(canSwim)
                        .canPushEntity(canPushEntity)
                        .canConvertToSource(canConvertToSource)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY))

        );

        this.prop = new BaseFlowingFluid.Properties(this.type, null, null);

        this.fluidsource = zFluids.zFluids.register(id + "_source",
                p -> new BaseFlowingFluid.Source(this.prop));

        this.fluidflowing = zFluids.zFluids.register(id + "_flowing",
                p -> new BaseFlowingFluid.Flowing(this.prop));

        this.itemBucket = noBucket ? null
                : zItems.zBucketItems.registerItem(id + "_bucket",
                        p -> new BucketItem(this.fluidsource.get(),
                                p.craftRemainder(Items.BUCKET).stacksTo(1)));

        this.block = zBlocks.zBlockFluids.registerBlock(
                id,
                pr -> new LiquidBlock(this.fluidsource.value(),
                        pr.mapColor(MapColor.WATER).replaceable().noCollision()
                                .strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid()
                                .sound(SoundType.EMPTY)
                                .liquid()
                                .lightLevel(dynLightLevel)
                                .emissiveRendering((s, g, p) -> lightLevel > 0 || dynLightLevel.applyAsInt(s) > 0)));

        var sampleProp = new BaseFlowingFluid.Properties(
                this.type,
                this.fluidsource,
                this.fluidflowing).block(this.block);

        if (!noBucket)
            sampleProp = sampleProp
                    .bucket(this.itemBucket);

        this.prop = sampleProp;
    }

    public DeferredHolder<Block, LiquidBlock> getBlock() {
        return block;
    }

    public DeferredHolder<Fluid, FlowingFluid> getFlowing() {
        return fluidflowing;
    }

    public DeferredHolder<Fluid, BaseFlowingFluid.Source> getSource() {
        return fluidsource;
    }

    public @Nullable DeferredHolder<Item, BucketItem> getItemBucket() {
        return itemBucket;
    }

    // public Identifier getStill() {
    // return still;
    // }

    public DeferredHolder<FluidType, ?> getType() {
        return type;
    }

    // public FluidRegister setTextures(Identifier still) {
    // this.still = still;
    // return this;
    // }

    // public FluidRegister setStillTexture(Identifier rl) {
    // this.still = rl;
    // return this;
    // }

    /**
     * dont work
     */
    public FluidRegister setLight(int l) {
        this.lightLevel = l;
        return this;
    }

    public FluidRegister setLight(ToIntFunction<BlockState> l) {
        this.dynLightLevel = l;
        return this;
    }

    public FluidRegister swim() {
        this.canSwim = true;
        return this;
    }

    public FluidRegister convertToSource() {
        this.canConvertToSource = true;
        return this;
    }

    public FluidRegister drown() {
        this.canDrown = true;
        return this;
    }

    public FluidRegister pushEntity() {
        this.canPushEntity = true;
        return this;
    }

    /**
     * Default value: 1000
     */
    public FluidRegister setViscosity(int v) {
        this.viscosity = v;
        return this;
    }

    public String getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public static FluidRegister create(String id, int color) {
        return create(id, color, false);
    }

    public static FluidRegister create(String id, Color color) {
        return create(id, ColorUtils.argb(color));
    }

    public static FluidRegister create(String id, Color color, boolean noBucket) {
        return create(id, ColorUtils.argb(color), noBucket);
    }

    public static FluidRegister create(String id, int color, boolean noBucket) {
        return new FluidRegister(id, color, noBucket);
    }

    public Fluid getFluid() {
        return getSource().get();
    }

}