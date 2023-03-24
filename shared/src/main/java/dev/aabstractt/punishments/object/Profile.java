package dev.aabstractt.punishments.object;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Set;

@RequiredArgsConstructor @Data
public final class Profile {

    private final @NonNull String id;

    private final @NonNull String name;
    private final @Nullable String lastName;

    private final @NonNull String currentAddress;
    private final @Nullable String lastAddress;

    private final @NonNull Set<Punishment> punishments;
}