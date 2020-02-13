package com.conquestreforged.core.block.properties;

import net.minecraft.util.IStringSerializable;

public enum BidirectionalShape implements IStringSerializable {
    NORTHSOUTH("northsouth"),
    EASTWEST("eastwest");

    private final String name;

    BidirectionalShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}
