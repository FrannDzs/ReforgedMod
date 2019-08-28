package com.conquestreforged.core.block.builder;

import com.conquestreforged.core.block.factory.BlockFactory;
import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.init.Context;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Props implements BlockFactory {

    private BlockName name = null;
    private BlockState base = null;
    private Material material = null;
    private MaterialColor color = null;
    private DyeColor dyeColor = null;
    private SoundType sound = null;
    private ItemGroup group = ItemGroup.SEARCH;
    private int light = Integer.MAX_VALUE;
    private float resistance = Float.MAX_VALUE;
    private float hardness = Float.MAX_VALUE;
    private float slipperiness = Float.MAX_VALUE;
    private boolean randomTick = false;
    private boolean variableOpacity = false;
    private boolean blocksMovement = true;
    private boolean floats = true;
    private Textures.Builder textures;
    private Map<String, Object> extradata = Collections.emptyMap();

    private boolean manual = false;

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
        if (base == null) {
            throw new InitializationException("Parent state is null");
        }
        return base;
    }

    public BlockState block() {
        return base;
    }

    public DyeColor dye() {
        return dyeColor == null ? DyeColor.BLACK : dyeColor;
    }

    public MaterialColor color() {
        return color == null ? MaterialColor.BLACK : color;
    }

    public Textures textures() {
        if (textures == null || textures.isEmpty()) {
            return Textures.NONE;
        }
        return textures.build();
    }

    public ItemGroup group() {
        return group;
    }

    public boolean isManual() {
        return manual;
    }

    public boolean floats() {
        return floats;
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

    public Props block(BlockState base) {
        this.base = base;
        return this;
    }

    public Props material(Material material) {
        this.material = material;
        return this;
    }

    public Props color(MaterialColor color) {
        this.color = color;
        return this;
    }

    public Props dye(DyeColor color) {
        this.dyeColor = color;
        return this;
    }

    public Props sound(SoundType sound) {
        this.sound = sound;
        return this;
    }

    public Props light(int light) {
        this.light = light;
        return this;
    }

    public Props strength(double hardness, double resistance) {
        this.hardness = (float) hardness;
        this.resistance = (float) resistance;
        return this;
    }

    public Props slipperiness(double slipperiness) {
        this.slipperiness = (float) slipperiness;
        return this;
    }

    public Props randomTick(boolean randomTick) {
        this.randomTick = randomTick;
        return this;
    }

    public Props opacity(boolean variableOpacity) {
        this.variableOpacity = variableOpacity;
        return this;
    }

    public Props blocking(boolean blocksMovement) {
        this.blocksMovement = blocksMovement;
        return this;
    }

    public Props floats(boolean floats) {
        this.floats = floats;
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

    public Props group(ItemGroup group) {
        this.group = group;
        return this;
    }

    public Props with(String key, Object data) {
        if (extradata.isEmpty()) {
            extradata = new HashMap<>();
        }
        extradata.put(key, data);
        return this;
    }

    public Block.Properties toProperties() throws InitializationException {
        Block.Properties builder = createBuilder();
        set(sound, null, builder::sound);
        setInt(light, builder::lightValue);
        setFloat(slipperiness, builder::slipperiness);
        setBool(randomTick, false, builder::tickRandomly);
        setBool(variableOpacity, false, builder::variableOpacity);
        setBool(blocksMovement, true, builder::doesNotBlockMovement);
        setFloats(resistance, hardness, builder::hardnessAndResistance);
        return builder;
    }

    private Block.Properties createBuilder() throws InitializationException {
        Block.Properties props;

        if (base != null) {
            props = Block.Properties.from(base.getBlock());
        } else if (color != null && material != null) {
            props = Block.Properties.create(material, color);
        } else if (material != null) {
            props = Block.Properties.create(material);
        } else {
            throw new InitializationException("Block.Builder requires a Material");
        }

        return props;
    }

    public static Props create() {
        return new Props();
    }

    public static Props create(BlockState state) {
        return create().block(state);
    }

    public static Props create(Block block) {
        return create(block.getDefaultState());
    }

    private static <V> void set(V value, V defValue, Consumer<V> consumer) {
        if (value != defValue) {
            consumer.accept(value);
        }
    }

    private static void setInt(int value, Consumer<Integer> consumer) {
        if (value != Integer.MAX_VALUE) {
            consumer.accept(value);
        }
    }

    private static void setFloat(float value, Consumer<Float> consumer) {
        if (value != Float.MAX_VALUE) {
            consumer.accept(value);
        }
    }

    private static void setBool(boolean value, boolean defValue, Runnable runnable) {
        if (value != defValue) {
            runnable.run();
        }
    }

    private static void setFloats(float value1, float value2, BiConsumer<Float, Float> consumer) {
        if (value1 != Float.MAX_VALUE && value2 != Float.MAX_VALUE) {
            consumer.accept(value1, value2);
        }
    }

    private static String withNamespace(String namespace, String name) {
        if (name.indexOf(':') != -1) {
            return name;
        }
        return namespace + ':' + name;
    }
}
