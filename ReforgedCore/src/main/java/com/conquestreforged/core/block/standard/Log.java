package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.RotatedPillarBlock;

@Assets(
        state = @State(name = "%s", template = "acacia_log"),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/acacia_log"),
        block = {
                @Model(name = "block/%s", template = "block/acacia_log")
        }
)
public class Log extends RotatedPillarBlock {
    public Log(Properties properties) {
        super(properties);
    }
}