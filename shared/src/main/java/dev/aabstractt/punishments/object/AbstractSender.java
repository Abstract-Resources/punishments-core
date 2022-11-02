package dev.aabstractt.punishments.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;

@AllArgsConstructor @Getter
public abstract class AbstractSender {

    private final @NonNull String id;
    @Setter private @NonNull String name;
    @Setter private @Nullable String lastName;

    private final @NonNull String currentAddress;
    @Setter private @Nullable String lastAddress;

    /**
     * if return true I'm going to call {@link AbstractSender#setName(String)} with the new name
     * and {@link AbstractSender#setLastName(String)} with the current profile name
     * @param updatedName String
     * @return boolean
     */
    public boolean isOutdated(String updatedName) {
        return !this.name.equalsIgnoreCase(updatedName);
    }

    public abstract void kick(@NonNull String message);

    public abstract void sendMessage(@NonNull String message);
}