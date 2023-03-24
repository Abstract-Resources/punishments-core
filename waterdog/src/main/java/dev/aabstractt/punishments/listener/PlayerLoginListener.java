package dev.aabstractt.punishments.listener;

import dev.aabstractt.punishments.AbstractPlugin;
import dev.aabstractt.punishments.factory.ProfileFactory;
import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import dev.aabstractt.punishments.sender.WaterdogSender;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.logger.Color;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.HashSet;

public final class PlayerLoginListener {

    public static void onPlayerLoginEvent(PlayerLoginEvent ev) {
        if (AbstractPlugin.getInstance().disabled()) {
            ev.setCancelReason(Color.RED + "Please wait while load AbstractPunishments!");

            ev.setCancelled(true);

            return;
        }

        ProxiedPlayer proxiedPlayer = ev.getPlayer();
        String currentName = proxiedPlayer.getName();
        String currentAddress = proxiedPlayer.getAddress().getAddress().getHostAddress();

        Profile profile = ProfileFactory.getInstance().loadProfile(
                proxiedPlayer.getXuid(),
                currentName,
                currentAddress
        );

        AbstractSender.store(new WaterdogSender(profile != null ? profile : new Profile(
                proxiedPlayer.getXuid(),
                currentName,
                currentName,
                currentAddress,
                currentAddress,
                new HashSet<>()
        )));
    }
}