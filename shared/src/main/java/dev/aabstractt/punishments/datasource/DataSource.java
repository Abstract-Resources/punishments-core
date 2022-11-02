package dev.aabstractt.punishments.datasource;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Set;

public interface DataSource {

    void init(@NonNull QueryDocument queryDocument);

    void storeQueryDocument(@NonNull QueryDocument dataSourceDocument);

    @Nullable QueryDocument getQueryDocument(@NonNull QueryDocument queryDocument);

    @NonNull Set<QueryDocument> getQueryDocuments(@NonNull QueryDocument queryDocument);

    void flushQueryDocument(@NonNull QueryDocument queryDocument);
}