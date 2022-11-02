package dev.aabstractt.punishments.command;

import dev.aabstractt.punishments.object.AbstractSender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Set;

@RequiredArgsConstructor @Getter
public abstract class BaseCommand<T> {

    private final @NonNull String name;
    private final @NonNull String permission;

    private final Set<String> aliases;

    public abstract void execute(@NonNull AbstractSender sender, @NonNull String commandName, @Nullable T target, @NonNull String[] args);

    public abstract @Nullable T parseTarget(@NonNull String argument);
}