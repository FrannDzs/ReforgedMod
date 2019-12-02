package com.conquestreforged.core.capability.handler;

import com.conquestreforged.core.networking.MessageHandler;
import net.minecraftforge.common.capabilities.Capability;

public interface Handler<T> extends Capability.IStorage<T>, MessageHandler<T> {

    String getName();
}
