package dev.aabstractt.punishments.command.defaults;

import dev.aabstractt.punishments.command.BaseCommand;
import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Set;

public final class BanCommand extends BaseCommand<Profile> {

    public BanCommand(@NonNull String name, @NonNull String permission, Set<String> aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(@NonNull AbstractSender sender, @NonNull String commandName, @Nullable Profile target, @NonNull String[] args) {
        // TODO: If target profile is null going to load it but using async to prevent lag spike on the main thread
    }

    @Override
    public @Nullable Profile parseTarget(@NonNull String argument) {
        return Profile.getIfLoaded(argument);
    }
}