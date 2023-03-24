package dev.aabstractt.punishments.listener;

import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;

public final class PlayerDisconnectListener {

    public static void onPlayerDisconnectEvent(PlayerDisconnectEvent ev) {
        // Flush the profile cache
        AbstractSender.flush(ev.getPlayer().getXuid());
    }
}