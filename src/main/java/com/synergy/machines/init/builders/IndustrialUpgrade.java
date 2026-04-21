package com.synergy.machines.init.builders;

import com.devdyna.cakesticklib.api.utils.UpgradeComponents;
import com.synergy.machines.api.machine.BaseMachineBE;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

@SuppressWarnings("null")
public class IndustrialUpgrade extends Item {

    public IndustrialUpgrade(Properties properties) {
        super(properties);
    }

    public IndustrialUpgrade() {
        this(new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext c) {
        var level = c.getLevel();
        var pos = c.getClickedPos();
        var item = c.getItemInHand();
        var be = level.getBlockEntity(pos);
        var player = c.getPlayer();

        if (player.isCrouching() && be instanceof BaseMachineBE machineBE) {

            if (machineBE.tryAddUpgrade(item)) {
                if (!player.isCreative())
                    item.shrink(1);
                return InteractionResult.SUCCESS;
            }

        }

        return InteractionResult.FAIL;

    }

    /**
     * Value 0 will exclude the modifier
     * <br/>
     * <br/>
     * 100 => x 1.0
     * <br/>
     * <br/>
     * 
     * @param s  speed %
     * @param ef energy usage %
     * @param l  secondary output luck %
     * @param f  fluid usage %
     * @param ec energy capacity %
     */
    public ItemStack set(int s, int e, int l, int f) {
        return UpgradeComponents.create(this, s, e, l, f);
    }

}