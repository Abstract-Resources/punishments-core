package dev.aabstractt.punishments.command;

import dev.aabstractt.punishments.object.AbstractSender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor @Getter
public abstract class BaseCommand<T> {

    private final @NonNull String name;
    private final @NonNull String permission;

    private final Set<String> aliases;

    private final Integer minArgs;
    private final @Nullable String usage;

    public abstract void execute(@NonNull AbstractSender sender, @NonNull String commandName, @NonNull T target, @NonNull String[] args);

    public abstract @NonNull CompletableFuture<T> attemptParseArgument(@NonNull String argument);
}