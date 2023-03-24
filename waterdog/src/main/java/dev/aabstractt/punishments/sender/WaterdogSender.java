package dev.aabstractt.punishments.sender;

import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import lombok.NonNull;

public final class WaterdogSender extends AbstractSender {

    public WaterdogSender(@NonNull Profile profile) {
        super(profile);
    }

    @Override
    public void kick(@NonNull String message) {
        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(this.profile.getName());

        if (proxiedPlayer == null) return;

        proxiedPlayer.disconnect(message);
    }

    @Override
    public void sendMessage(@NonNull String message) {
        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(this.profile.getName());

        if (proxiedPlayer == null) return;

        proxiedPlayer.sendMessage(message);
    }
}