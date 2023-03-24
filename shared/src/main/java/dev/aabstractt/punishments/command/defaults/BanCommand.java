package dev.aabstractt.punishments.command.defaults;

import dev.aabstractt.punishments.command.BaseCommand;
import dev.aabstractt.punishments.factory.ProfileFactory;
import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class BanCommand extends BaseCommand<Profile> {

    public BanCommand(@NonNull String name, @NonNull String permission, Set<String> aliases, Integer minArgs, @Nullable String usage) {
        super(name, permission, aliases, minArgs, usage);
    }

    @Override
    public void execute(@NonNull AbstractSender sender, @NonNull String commandName, @NonNull Profile target, @NonNull String[] args) {
        // TODO: If target profile is null going to load it but using async to prevent lag spike on the main thread
    }

    @Override
    public @NonNull CompletableFuture<Profile> attemptParseArgument(@NonNull String argument) {
        AbstractSender abstractSender = AbstractSender.getIfLoaded(argument);

        if (abstractSender != null) {
            return CompletableFuture.completedFuture(abstractSender.getProfile());
        }

        CompletableFuture<Profile> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            Profile profile = ProfileFactory.getInstance().loadProfile(argument);

            if (profile == null) {
                completableFuture.completeExceptionally(new RuntimeException("Player not found"));

                return;
            }

            completableFuture.complete(profile);
        });

        return completableFuture;
    }
}