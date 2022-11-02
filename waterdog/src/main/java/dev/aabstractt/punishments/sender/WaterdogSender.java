package dev.aabstractt.punishments.sender;

import dev.aabstractt.punishments.object.AbstractSender;
import lombok.NonNull;

import javax.annotation.Nullable;

public final class WaterdogSender extends AbstractSender {

    public WaterdogSender(@NonNull String id, @NonNull String name, @Nullable String lastName, @NonNull String currentAddress, @Nullable String lastAddress) {
        super(id, name, lastName, currentAddress, lastAddress);
    }

    @Override
    public void kick(@NonNull String message) {

    }

    @Override
    public void sendMessage(@NonNull String message) {

    }
}