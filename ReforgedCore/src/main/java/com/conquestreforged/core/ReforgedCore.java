package com.conquestreforged.core;

public class ReforgedCore {

    private static final ReforgedCore instance = new ReforgedCore();

    private boolean dumpAssets = false;
    private boolean prettyAssets = false;

    private ReforgedCore() {
        setDumpAssets(true);
    }

    public void setDumpAssets(boolean value) {
        this.dumpAssets = value;
    }

    public void setPrettyAssets(boolean value) {
        this.prettyAssets = value;
    }

    public boolean dumpAssets() {
        return dumpAssets;
    }

    public boolean prettyAssets() {
        return prettyAssets;
    }

    public static ReforgedCore getInstance() {
        return instance;
    }
}
