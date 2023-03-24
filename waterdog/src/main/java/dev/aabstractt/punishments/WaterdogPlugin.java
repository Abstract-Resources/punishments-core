package dev.aabstractt.punishments;

import com.google.common.collect.ImmutableSet;
import dev.aabstractt.punishments.command.MainCommand;
import dev.aabstractt.punishments.command.defaults.BanCommand;
import dev.aabstractt.punishments.listener.PlayerDisconnectListener;
import dev.aabstractt.punishments.listener.PlayerLoginListener;
import dev.aabstractt.punishments.object.Profile;
import dev.aabstractt.punishments.sender.WaterdogSender;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;
import lombok.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class WaterdogPlugin extends Plugin {

    public final static @NonNull WaterdogSender ConsoleSender = new WaterdogSender(new Profile(
            "0",
            "CONSOLE",
            "CONSOEL",
            "127.0.0.1",
            "127.0.0.1",
            new HashSet<>()
    ));

    @Override
    public void onEnable() {
        Configuration configuration = this.getConfig();

        String providerName = configuration.getString("provider.type");
        Object providerAuth = configuration.get("provider." + providerName.toLowerCase());

        if (!AbstractPlugin.getInstance().init(new PluginConfig(
                providerName,
                providerAuth instanceof Map ? (Map<String, Object>) providerAuth : new HashMap<>()
        ))) {
            this.getLogger().error("An error occurred while tried load the AbstractPunishments plugin!");

            ProxyServer.getInstance().shutdown();

            return;
        }

        this.getProxy().getEventManager().subscribe(PlayerLoginEvent.class, PlayerLoginListener::onPlayerLoginEvent);
        this.getProxy().getEventManager().subscribe(PlayerDisconnectEvent.class, PlayerDisconnectListener::onPlayerDisconnectEvent);

        this.getProxy().getCommandMap().registerCommand(new MainCommand<>(new BanCommand(
                "ban",
                "abstractpunishments.command.ban",
                ImmutableSet.<String>builder().add("ipban").build(),
                1,
                "/<command> <player> [format] <reason> [?-s]"
                )
        ));

        // TODO: Load the listeners
    }
}