package dev.aabstractt.punishments.factory;

import dev.aabstractt.punishments.AbstractPlugin;
import dev.aabstractt.punishments.datasource.QueryDocument;
import dev.aabstractt.punishments.object.AbstractSender;
import dev.aabstractt.punishments.object.Profile;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashSet;

public final class ProfileFactory {

    @Getter private final static @NonNull ProfileFactory instance = new ProfileFactory();

    // MongoDB Collections
    private final static @NonNull String PROFILE_COLLECTION = "profiles";

    // MySQL Statements
    private final static @NonNull String LOAD_PROFILE_STATEMENT = "load_profile";
    private final static @NonNull String UPDATE_PROFILE_STATEMENT = "update_profile";

    /**
     * @param sender  Sender is the Player instance
     */
    public void loadProfile(@NonNull AbstractSender sender) {
        if (!AbstractPlugin.getInstance().isLoaded()) return;

        QueryDocument queryDocument = AbstractPlugin.getDataSource().getQueryDocument(QueryDocument.builder()
                .collection(PROFILE_COLLECTION)
                .statement(LOAD_PROFILE_STATEMENT)
                .append(0, "id", sender.getId())
                .build()
        );

        @Nullable String currentAddress = queryDocument != null ? queryDocument.getStringNonNull("current_address") : null;
        sender.setLastAddress(
                currentAddress != null && currentAddress.equals(sender.getCurrentAddress()) ? queryDocument.getStringNonNull("last_address") : currentAddress
        );

        String currentName = queryDocument != null ? queryDocument.getStringNonNull("username") : null;
        if (currentName == null || !currentName.equalsIgnoreCase(sender.getName())) {
            this.storeProfile(sender);
        }

        Profile.store(new Profile(
                sender,
                queryDocument != null ? PunishmentFactory.getInstance().loadPunishments(sender.getId()) : new HashSet<>()
        ));
    }

    public void storeProfile(@NonNull AbstractSender sender) {
        if (!AbstractPlugin.getInstance().isLoaded()) return;

        AbstractPlugin.getDataSource().storeQueryDocument(QueryDocument.builder()
                .collection(PROFILE_COLLECTION)
                .statement(UPDATE_PROFILE_STATEMENT)
                .append(0, "id", sender.getId())
                .append(1, "username", sender.getName())
                .append(2, "last_name", sender.getLastName())
                .append(3, "current_address", sender.getCurrentAddress())
                .append(4, "last_address", sender.getLastAddress())
                .append(5, "last_logout", null)
                .build()
        );
    }
}