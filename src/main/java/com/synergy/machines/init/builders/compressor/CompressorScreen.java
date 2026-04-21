package com.synergy.machines.init.builders.compressor;


import static com.synergy.machines.Main.MODULE_ID;

import com.devdyna.cakesticklib.api.utils.x;
import com.synergy.machines.api.machine.BaseMachineScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@SuppressWarnings("null")
public class CompressorScreen extends BaseMachineScreen<CompressorMenu> {

    public CompressorScreen(CompressorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

@Override
public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
    if (whenAnimateArrow())
            guiGraphics.blit(
                    RenderPipelines.GUI, x.rl(MODULE_ID,"textures/gui/sprite/compressor_arrow.png"),
                    getLeftPos() + 47,
                    getTopPos() + 36,
                    1,0, 0,
                    16, 9,
                    16, 9);
                    
    super.extractBackground(guiGraphics, mouseX, mouseY, a);
}
    
 

    

}
