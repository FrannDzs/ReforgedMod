package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.RotatedPillarBlock;

@Assets(
        state = @State(name = "%s", template = "parent_log_furniture"),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/acacia_log"),
        block = {
                @Model(name = "block/%s", template = "block/acacia_log"),
                @Model(name = "block/%s_side", template = "block/parent_log_furniture_side")
        }
)
public class LogFurniture extends RotatedPillarBlock {
    public LogFurniture(Properties properties) {
        super(properties);
    }
}