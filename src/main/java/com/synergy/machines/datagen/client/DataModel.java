package com.synergy.machines.datagen.client;

import static com.synergy.machines.Main.MODULE_ID;

import com.synergy.machines.init.Material;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class DataModel extends ModelProvider {

        public DataModel(PackOutput output) {
                super(output, MODULE_ID);
        }

        @Override
        protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

                Material.getAllMachineTypes()
                                .forEach(m -> blockModels.createRotatableColumn(m.block().get()));

        }

}
