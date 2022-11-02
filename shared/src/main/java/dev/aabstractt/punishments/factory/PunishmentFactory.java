package dev.aabstractt.punishments.factory;

import dev.aabstractt.punishments.AbstractPlugin;
import dev.aabstractt.punishments.datasource.QueryDocument;
import dev.aabstractt.punishments.object.Punishment;
import dev.aabstractt.punishments.object.Punishment.PunishmentType;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class PunishmentFactory {

    @Getter private final static @NonNull PunishmentFactory instance = new PunishmentFactory();

    // MongoDB Collections
    private final static @NonNull String PUNISHMENTS_COLLECTION = "punishments";

    // MySQL Statements
    private final static @NonNull String LOAD_PUNISHMENTS_STATEMENT = "load_punishments";

    public @NonNull Set<Punishment> loadPunishments(@NonNull String id) {
        if (!AbstractPlugin.getInstance().isLoaded()) return new HashSet<>();

        Set<QueryDocument> queryDocuments = AbstractPlugin.getDataSource().getQueryDocuments(QueryDocument.builder()
                .collection(PUNISHMENTS_COLLECTION)
                .statement(LOAD_PUNISHMENTS_STATEMENT)
                .append(0, "target_id", id)
                .build()
        );

        return queryDocuments.stream()
                .map(queryDocument -> Punishment.builder()
                        .type(PunishmentType.fromString(queryDocument.getStringNonNull("type")))
                        .targetAddress(queryDocument.getStringNonNull("target_address"))
                        .targetName(queryDocument.getStringNonNull("target_name"))
                        .targetId(queryDocument.getStringNonNull("target_id"))
                        .senderId(queryDocument.getStringNonNull("sender_id"))
                        .senderName(queryDocument.getStringNonNull("sender_name"))
                        .createdAt(queryDocument.getStringNonNull("created_at"))
                        .endAt(queryDocument.getStringNonNull("end_at"))
                        .reason(queryDocument.getStringNonNull("reason"))
                        .withoutReason(queryDocument.getBoolean("without_reason"))
                        .silent(queryDocument.getBoolean("silent"))
                        .ip(queryDocument.getBoolean("ip"))
                        .id(queryDocument.getInteger("id"))
                        .build())
                .collect(Collectors.toSet());
    }
}