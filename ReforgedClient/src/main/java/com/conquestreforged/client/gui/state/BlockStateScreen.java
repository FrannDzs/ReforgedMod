package com.conquestreforged.client.gui.state;

import com.conquestreforged.client.gui.PickerScreen;
import com.conquestreforged.client.gui.render.ModelRender;
import com.conquestreforged.core.item.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;

public class BlockStateScreen extends PickerScreen<BlockState> {

    public BlockStateScreen(ItemStack stack, BlockState state) {
        super("State Selector", stack, state, state.getBlock().getStateContainer().getValidStates());
    }

    @Override
    public int getWidth(BlockState option) {
        return 16;
    }

    @Override
    public int getHeight(BlockState option) {
        return 16;
    }

    @Override
    public String getDisplayName(BlockState option) {
        return option.getBlock().getNameTextComponent().getString();
    }

    @Override
    public void render(BlockState option, int x, int y, int width, int height) {
        IBakedModel model = Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(option);
        ModelRender.renderModel(model, x, y, 0xFFFFFF);
    }

    //    (BlockState blockStateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferTypeIn, int combinedLightIn, int combinedOverlayIn, net.minecraftforge.client.model.data.IModelData modelData) {

    @Override
    public ItemStack createItemStack(ItemStack original, BlockState value) {
        ItemStack stack = ItemUtils.fromState(value);
        stack.setCount(original.getCount());
        return stack;
    }
}
