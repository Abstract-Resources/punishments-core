package dev.aabstractt.punishments.object;

import dev.aabstractt.punishments.datasource.DataSourceException;
import lombok.*;

@RequiredArgsConstructor (access = AccessLevel.PRIVATE) @Data @Builder
public final class Punishment {

    private final @NonNull PunishmentType type;

    private final @NonNull String targetAddress;
    private final @NonNull String targetId;
    private final @NonNull String targetName;

    private final @NonNull String senderId;
    private final @NonNull String senderName;

    private final @NonNull String createdAt;
    private final @NonNull String endAt;

    private final @NonNull String reason;
    private final boolean withoutReason;
    private final boolean silent;
    private final boolean ip;

    private final int id;

    public enum PunishmentType {
        KICK(),
        WARN(),
        UNWARN(),
        MUTE(),
        UNMUTE(),
        BAN(),
        UNBAN();

        public static @NonNull PunishmentType[] all() {
            return new PunishmentType[]{MUTE, BAN};
        }

        public static @NonNull PunishmentType[] allBan() {
            return new PunishmentType[]{BAN, UNBAN};
        }

        public static @NonNull PunishmentType[] allMute() {
            return new PunishmentType[]{MUTE, UNMUTE};
        }

        public static @NonNull PunishmentType fromString(String type) {
            if (type.equals("K")) return KICK;
            if (type.equals("W")) return WARN;
            if (type.equals("UW")) return UNWARN;
            if (type.equals("M")) return MUTE;
            if (type.equals("UM")) return UNMUTE;
            if (type.equals("B")) return BAN;
            if (type.equals("UB")) return UNBAN;

            throw new DataSourceException("An invalid Punishment Type received.");
        }

        public @NonNull String toString() {
            if (this.equals(KICK)) return "K";
            if (this.equals(WARN)) return "W";
            if (this.equals(UNWARN)) return "UW";
            if (this.equals(MUTE)) return "M";
            if (this.equals(UNMUTE)) return "UM";
            if (this.equals(BAN)) return "B";
            if (this.equals(UNBAN)) return "UB";

            throw new DataSourceException("Please provide a valid Punishment");
        }
    }
}