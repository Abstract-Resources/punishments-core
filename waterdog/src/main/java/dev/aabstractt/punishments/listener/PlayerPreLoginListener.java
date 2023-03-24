package dev.aabstractt.punishments.listener;

import dev.aabstractt.punishments.AbstractPlugin;
import dev.aabstractt.punishments.factory.ProfileFactory;
import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import dev.aabstractt.punishments.sender.WaterdogSender;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;
import dev.waterdog.waterdogpe.logger.Color;
import dev.waterdog.waterdogpe.network.session.LoginData;

import java.util.HashSet;

public final class PlayerPreLoginListener {

    public static void onPlayerPreLoginEvent(PlayerPreLoginEvent ev) {
        if (!AbstractPlugin.getInstance().isLoaded()) {
            ev.setCancelReason(Color.RED + "Please wait while load AbstractPunishments!");

            ev.setCancelled(true);

            return;
        }

        LoginData loginData = ev.getLoginData();

        ProxyServer.getInstance().getScheduler().scheduleAsync(() -> {
            Profile profile = ProfileFactory.getInstance().loadProfile(
                    loginData.getXuid(),
                    loginData.getDisplayName(),
                    ev.getAddress().getAddress().getHostAddress()
            );

            AbstractSender.store(new WaterdogSender(profile != null ? profile : new Profile(
                    loginData.getXuid(),
                    loginData.getDisplayName(),
                    loginData.getDisplayName(),
                    loginData.getAddress().getAddress().getHostAddress(),
                    loginData.getAddress().getAddress().getHostAddress(),
                    new HashSet<>()
            )));

            // TODO: Profile successfully loaded!
        });
    }
}