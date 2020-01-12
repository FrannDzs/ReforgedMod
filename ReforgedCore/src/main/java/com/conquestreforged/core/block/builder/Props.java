package com.conquestreforged.core.block.builder;

import com.conquestreforged.core.block.data.BlockTemplate;
import com.conquestreforged.core.block.data.ColorType;
import com.conquestreforged.core.block.factory.BlockFactory;
import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.init.Context;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Props extends BlockProps<Props> implements BlockFactory {

    /**
     * Certain Block constructor methods need a BlockState passing to them, ie a 'parent'.
     * For example, Slabs need the full-block instance passing to them to act as the double-slab variant.
     *
     * In cases where the first Block created with the Factory requires a parent Block/BlockState,
     * the 'parent' must be set manually before calling the register(..) methods.
     *
     * We otherwise assume the first Block created with this Factory is the parent.
     * This Block should therefore NOT require a parent Block or BlockState in it's own constructor.
     */
    private BlockState parent = null;
    private BlockName name = null;
    private ColorType colorType = ColorType.NONE;
    private Textures.Builder textures;
    private Map<String, Object> extradata = Collections.emptyMap();

    private boolean manual = false;

    private Props(Block block) {
        super(block);
    }

    private Props(Material material) {
        super(material);
    }

    private Props(Material material, MaterialColor color) {
        super(material, color);
    }

    private Props(Props props) {
        super(props);
        this.parent = props.parent;
        this.name = props.name;
        this.colorType = props.colorType;
        this.textures = props.textures;
        this.extradata = props.extradata;
    }

    @Override
    public Props getProps() {
        return this;
    }

    @Override
    public BlockName getName() {
        if (name == null) {
            throw new InitializationException("Block name is null");
        }
        return name;
    }

    @Override
    public BlockState getParent() throws InitializationException {
        if (parent == null) {
            throw new InitializationException("Parent state is null");
        }
        return parent;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public Textures textures() {
        if (textures == null || textures.isEmpty()) {
            return Textures.NONE;
        }
        return textures.build();
    }

    public boolean isManual() {
        return manual;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public <T> T get(String key, Class<T> type) {
        Object o = extradata.get(key);
        if (o == null) {
            throw new InitializationException(
                    new NullPointerException(key + ": value is null")
            );
        }
        if (!type.isInstance(o)) {
            throw new InitializationException(
                    new ClassCastException(key + ": expected " + type + " but found " + o.getClass())
            );
        }
        return type.cast(o);
    }

    public Props manual() {
        manual = true;
        return this;
    }

    /**
     * Set the 'parent' (usually the full-block variant) of all subsequent Blocks created by this factory.
     *
     * If not set manually, the first Block instance created by this Factory will be set as the parent. In this case,
     * it's critical that this first Block does not itself require a parent Block/BlockState in it's constructor.
     *
     * @param state The parent BlockState to use
     * @return this Props instance (for chaining calls)
     */
    public Props parent(BlockState state) {
        this.parent = state;
        return this;
    }

    public Props name(String namespace, String plural, String singular) {
        return name(BlockName.of(namespace, plural, singular));
    }

    public Props name(String plural, String singular) {
        return name(Context.getInstance().getNamespace(), plural, singular);
    }

    public Props name(String name) {
        return name(Context.getInstance().getNamespace(), name, name);
    }

    public Props name(BlockName name) {
        this.name = name;
        return this;
    }

    public Props grassColor() {
        colorType = ColorType.GRASS;
        return this;
    }

    public Props foliageColor() {
        colorType = ColorType.FOLIAGE;
        return this;
    }

    public Props waterColor() {
        colorType = ColorType.WATER;
        return this;
    }

    public Props texture(String texture) {
        return texture("*", texture);
    }

    public Props texture(String name, String texture) {
        String namespace = Context.getInstance().getNamespace();
        String path = texture;

        int i = texture.indexOf(':');
        if (i != -1) {
            namespace = texture.substring(0, i);
            path = texture.substring(i + 1);
        }

        int j = path.indexOf('/');
        if (j == -1) {
            path = "block/" + path;
        }

        if (textures == null) {
            textures = Textures.builder();
        }

        textures.add(name, withNamespace(namespace, path));
        return this;
    }

    public Props template(BlockTemplate template) {
        if (template.getRenderLayer().isCutout()) {
            Props props = new Props(this);
            props.solid(false);
            return props;
        }
        return this;
    }

    public Props with(String key, Object data) {
        if (extradata.isEmpty()) {
            extradata = new HashMap<>();
        }
        extradata.put(key, data);
        return this;
    }

    public static Props create(Block block) {
        Preconditions.checkNotNull(block, "Block must not be null");
        return new Props(block);
    }

    public static Props create(BlockState state) {
        Preconditions.checkNotNull(state, "BlockState must not be null");
        return create(state.getBlock());
    }

    public static Props create(Material material) {
        Preconditions.checkNotNull(material, "Material must not be null");
        return new Props(material);
    }

    public static Props create(Material material, MaterialColor color) {
        Preconditions.checkNotNull(material, "Material must not be null");
        Preconditions.checkNotNull(color, "MaterialColor must not be null");
        return new Props(material, color);
    }

    private static String withNamespace(String namespace, String name) {
        if (name.indexOf(':') != -1) {
            return name;
        }
        return namespace + ':' + name;
    }
}
