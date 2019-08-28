package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s_segmental_arch_half", template = "parent_segmental_arch_half"),
        item = @Model(name = "item/%s_segmental_arch_half", parent = "block/%s_segmental_arch_one_half", template = "item/parent_segmental_arch_half"),
        block = {
                @Model(name = "block/%s_segmental_arch_one_half", template = "block/parent_segmental_arch_one_half"),
                @Model(name = "block/%s_segmental_arch_two_half_l", template = "block/parent_segmental_arch_two_half_l"),
                @Model(name = "block/%s_segmental_arch_two_half_r", template = "block/parent_segmental_arch_two_half_r"),
                @Model(name = "block/%s_segmental_arch_three_half_l", template = "block/parent_segmental_arch_three_half_l"),
                @Model(name = "block/%s_segmental_arch_three_half_r", template = "block/parent_segmental_arch_three_half_r"),
                @Model(name = "block/%s_segmental_arch_three_top_half", template = "block/parent_segmental_arch_three_top_half"),
        }
)
public class ArchSegmentalHalf extends ArchRoundHalf {

    public ArchSegmentalHalf(Properties properties) {
        super(properties);
    }

}