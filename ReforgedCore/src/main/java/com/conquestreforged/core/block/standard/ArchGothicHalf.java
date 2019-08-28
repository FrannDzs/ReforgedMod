package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s_gothic_arch_half", template = "parent_gothic_arch_half"),
        item = @Model(name = "item/%s_gothic_arch_half", parent = "block/%s_gothic_arch_one_half", template = "item/parent_gothic_arch_half"),
        block = {
                @Model(name = "block/%s_gothic_arch_one_half", template = "block/parent_gothic_arch_one_half"),
                @Model(name = "block/%s_gothic_arch_two_half_l", template = "block/parent_gothic_arch_two_half_l"),
                @Model(name = "block/%s_gothic_arch_two_half_r", template = "block/parent_gothic_arch_two_half_r"),
                @Model(name = "block/%s_gothic_arch_three_half_l", template = "block/parent_gothic_arch_three_half_l"),
                @Model(name = "block/%s_gothic_arch_three_half_r", template = "block/parent_gothic_arch_three_half_r"),
                @Model(name = "block/%s_gothic_arch_three_top_half", template = "block/parent_gothic_arch_three_top_half"),
        }
)
public class ArchGothicHalf extends ArchRoundHalf {

    public ArchGothicHalf(Properties properties) {
        super(properties);
    }

}