package dev.aabstractt.punishments.listener;

import dev.aabstractt.punishments.object.Profile;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;

public final class PlayerDisconnectListener {

    public void onPlayerDisconnectEvent(PlayerDisconnectEvent ev) {
        // Flush the profile cache
        Profile.flush(ev.getPlayer().getXuid());
    }
}