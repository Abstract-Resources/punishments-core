package dev.aabstractt.punishments.object;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor @Getter
public final class Profile {

    private final static Map<String, Profile> profilesStored = Maps.newConcurrentMap();

    private final @NonNull AbstractSender abstractSender;
    private final @NonNull Set<Punishment> punishments;

    public static void store(@NonNull Profile profile) {
        profilesStored.put(profile.getAbstractSender().getId(), profile);
    }

    public static void flush(@NonNull String id) {
        Profile profile = profilesStored.get(id);

        if (profile == null) return;

        // TODO: Flush all profile object cache
        profile.getPunishments().clear();

        profilesStored.remove(id);
    }
}