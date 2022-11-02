package dev.aabstractt.punishments.listener;

import dev.aabstractt.punishments.AbstractPlugin;
import dev.aabstractt.punishments.factory.ProfileFactory;
import dev.aabstractt.punishments.sender.WaterdogSender;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;
import dev.waterdog.waterdogpe.logger.Color;

public final class PlayerPreLoginListener {

    public void onPlayerPreLoginEvent(PlayerPreLoginEvent ev) {
        if (!AbstractPlugin.getInstance().isLoaded()) {
            ev.setCancelReason(Color.RED + "Please wait while load AbstractPunishments!");

            ev.setCancelled(true);

            return;
        }

        ProxyServer.getInstance().getScheduler().scheduleAsync(() -> {
            ProfileFactory.getInstance().loadProfile(new WaterdogSender(
                    ev.getLoginData().getXuid(),
                    ev.getLoginData().getDisplayName(),
                    null,
                    ev.getAddress().getAddress().getHostAddress(),
                    null
            ));

            // TODO: Profile successfully loaded!
        });
    }
}