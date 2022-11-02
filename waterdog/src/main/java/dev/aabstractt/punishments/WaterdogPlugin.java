package dev.aabstractt.punishments;

import dev.waterdog.waterdogpe.ProxyServer;
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

        // TODO: Load the listeners
    }
}