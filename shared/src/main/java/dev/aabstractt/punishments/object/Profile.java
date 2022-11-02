package dev.aabstractt.punishments.object;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor @Getter
public final class Profile {

    private final static @NonNull Map<String, Profile> profilesStored = Maps.newConcurrentMap();
    private final static @NonNull Map<String, String> idsStored = Maps.newConcurrentMap();

    private final @NonNull AbstractSender abstractSender;
    private final @NonNull Set<Punishment> punishments;

    public static void store(@NonNull Profile profile) {
        AbstractSender abstractSender = profile.getAbstractSender();

        profilesStored.put(abstractSender.getId(), profile);
        idsStored.put(abstractSender.getName().toLowerCase(), abstractSender.getId());
    }

    public static void flush(@NonNull String id) {
        Profile profile = profilesStored.get(id);

        if (profile == null) return;

        // TODO: Flush all profile object cache
        profile.getPunishments().clear();

        profilesStored.remove(id);
    }

    public static @Nullable Profile getIfLoaded(@NonNull String idOrName) {
        Profile profile = profilesStored.get(idOrName);

        if (profile != null) return profile;

        idOrName = idsStored.get(idOrName);
        return idOrName != null ? Profile.getIfLoaded(idOrName) : null;
    }
}