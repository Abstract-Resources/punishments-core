package dev.aabstractt.punishments;

import dev.aabstractt.punishments.listener.PlayerDisconnectListener;
import dev.aabstractt.punishments.listener.PlayerPreLoginListener;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;

import java.util.HashMap;
import java.util.Map;

public final class WaterdogPlugin extends Plugin {

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

        this.getProxy().getEventManager().subscribe(PlayerPreLoginEvent.class, PlayerPreLoginListener::onPlayerPreLoginEvent);
        this.getProxy().getEventManager().subscribe(PlayerDisconnectEvent.class, PlayerDisconnectListener::onPlayerDisconnectEvent);

        // TODO: Load the listeners
    }
}