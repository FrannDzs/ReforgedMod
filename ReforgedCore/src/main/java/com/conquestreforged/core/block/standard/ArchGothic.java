package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s_gothic_arch", template = "parent_gothic_arch"),
        item = @Model(name = "item/%s_gothic_arch", parent = "block/%s_gothic_arch_one", template = "item/parent_gothic_arch"),
        block = {
                @Model(name = "block/%s_gothic_arch_one", template = "block/parent_gothic_arch_one"),
                @Model(name = "block/%s_gothic_arch_two", template = "block/parent_gothic_arch_two"),
                @Model(name = "block/%s_gothic_arch_three", template = "block/parent_gothic_arch_three"),
                @Model(name = "block/%s_gothic_arch_three_top", template = "block/parent_gothic_arch_three_top"),
        }
)
public class ArchGothic extends ArchRound {

    public ArchGothic(Properties properties) {
        super(properties);
    }

}