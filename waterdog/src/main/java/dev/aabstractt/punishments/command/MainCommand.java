package dev.aabstractt.punishments.command;

import dev.aabstractt.punishments.WaterdogPlugin;
import dev.aabstractt.punishments.object.AbstractSender;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.logger.Color;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import lombok.NonNull;

public final class MainCommand<T> extends Command {

    private final @NonNull BaseCommand<T> baseCommand;

    public MainCommand(BaseCommand<T> baseCommand) {
        super(baseCommand.getName(), CommandSettings.builder()
                .setAliases(baseCommand.getAliases().toArray(new String[0]))
                .setPermission(baseCommand.getPermission())
                .build()
        );

        this.baseCommand = baseCommand;
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String s, String[] args) {
        if (args.length < this.baseCommand.getMinArgs()) {
            commandSender.sendMessage(this.baseCommand.getUsage());

            return true;
        }

        AbstractSender abstractSender = commandSender instanceof ProxiedPlayer ? AbstractSender.getIfLoaded(((ProxiedPlayer) commandSender).getXuid()) : WaterdogPlugin.ConsoleSender;
        if (abstractSender == null) {
            return true;
        }

        this.baseCommand.attemptParseArgument(args[0]).whenComplete((argumentParsed, throwable) -> {
            if (throwable != null) {
                commandSender.sendMessage(Color.RED + throwable.getMessage());

                return;
            }

            this.baseCommand.execute(abstractSender, s, argumentParsed, args);
        });

        return false;
    }
}