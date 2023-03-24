package dev.aabstractt.punishments.object;

import com.google.common.collect.Maps;
import lombok.*;

import javax.annotation.Nullable;
import java.util.Map;

@AllArgsConstructor @Data
public abstract class AbstractSender {

    private final static @NonNull Map<String, AbstractSender> sendersStored = Maps.newConcurrentMap();
    private final static @NonNull Map<String, String> idsStored = Maps.newConcurrentMap();

    protected final @NonNull Profile profile;

    public abstract void kick(@NonNull String message);

    public abstract void sendMessage(@NonNull String message);

    public static void store(@NonNull AbstractSender abstractSender) {
        Profile profile = abstractSender.getProfile();

        sendersStored.put(profile.getId(), abstractSender);
        idsStored.put(profile.getName().toLowerCase(), profile.getId());
    }

    public static void flush(@NonNull String id) {
        AbstractSender abstractSender = sendersStored.get(id);

        if (abstractSender == null) return;

        // TODO: Flush all profile object cache
        abstractSender.getProfile().getPunishments().clear();

        sendersStored.remove(id);
    }

    public static @Nullable AbstractSender getIfLoaded(@NonNull String idOrName) {
        AbstractSender abstractSender = sendersStored.get(idOrName);

        if (abstractSender != null) return abstractSender;

        idOrName = idsStored.get(idOrName);
        return idOrName != null ? getIfLoaded(idOrName) : null;
    }
}