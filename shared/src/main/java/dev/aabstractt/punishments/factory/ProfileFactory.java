package dev.aabstractt.punishments.factory;

import dev.aabstractt.punishments.AbstractPlugin;
import dev.aabstractt.punishments.datasource.QueryDocument;
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

    public @Nullable Profile loadProfile(@NonNull String currentName) {
        if (!AbstractPlugin.getInstance().isLoaded()) return null;

        QueryDocument queryDocument = AbstractPlugin.getDataSource().getQueryDocument(QueryDocument.builder()
                .collection(PROFILE_COLLECTION)
                .statement(LOAD_PROFILE_STATEMENT)
                .append(0, "current_name", currentName)
                .build()
        );

        if (queryDocument == null) return null;

        String currentStoredName = queryDocument.getStringNonNull("current_name");
        String currentStoredAddress = queryDocument.getStringNonNull("current_address");

        return this.loadProfile(
                queryDocument.getStringNonNull("id"),
                currentStoredName,
                currentStoredAddress,
                currentStoredName,
                currentStoredAddress
        );
    }

    public @Nullable Profile loadProfile(@NonNull String id, @NonNull String currentName, @NonNull String currentAddress) {
        if (!AbstractPlugin.getInstance().isLoaded()) return null;

        QueryDocument queryDocument = AbstractPlugin.getDataSource().getQueryDocument(QueryDocument.builder()
                .collection(PROFILE_COLLECTION)
                .statement(LOAD_PROFILE_STATEMENT)
                .append(0, "id", id)
                .build()
        );

        if (queryDocument == null) return null;

        String currentStoredName = queryDocument.getStringNonNull("current_name");
        String currentStoredAddress = queryDocument.getStringNonNull("current_address");

        return this.loadProfile(
                id,
                currentName,
                currentAddress,
                currentStoredName,
                currentStoredAddress
        );
    }

    public @NonNull Profile loadProfile(@NonNull String id, @NonNull String currentName, @NonNull String currentAddress, @NonNull String currentStoredName, @NonNull String currentStoredAddress) {
        return new Profile(
                id,
                currentName,
                currentStoredName.equals(currentName) ? currentName : currentStoredName,
                currentAddress,
                currentStoredAddress.equalsIgnoreCase(currentAddress) ? currentAddress : currentStoredAddress,
                new HashSet<>()
        );
    }

    public void updateProfile(@NonNull Profile profile) {
        if (!AbstractPlugin.getInstance().isLoaded()) return;

        AbstractPlugin.getDataSource().storeQueryDocument(QueryDocument.builder()
                .collection(PROFILE_COLLECTION)
                .statement(UPDATE_PROFILE_STATEMENT)
                .append(0, "id", profile.getId())
                .append(1, "current_name", profile.getName())
                .append(2, "last_name", profile.getLastName())
                .append(3, "current_address", profile.getCurrentAddress())
                .append(4, "last_address", profile.getLastAddress())
                .append(5, "last_logout", null)
                .build()
        );
    }
}