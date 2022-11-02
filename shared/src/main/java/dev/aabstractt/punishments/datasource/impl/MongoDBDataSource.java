package dev.aabstractt.punishments.datasource.impl;

import dev.aabstractt.punishments.datasource.DataSource;
import dev.aabstractt.punishments.datasource.QueryDocument;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Set;

public final class MongoDBDataSource implements DataSource {

    @Override
    public void init(@NonNull QueryDocument queryDocument) {

    }

    @Override
    public void storeQueryDocument(@NonNull QueryDocument dataSourceDocument) {

    }

    @Nullable
    @Override
    public QueryDocument getQueryDocument(@NonNull QueryDocument queryDocument) {
        return null;
    }

    @Override
    public @NonNull Set<QueryDocument> getQueryDocuments(@NonNull QueryDocument queryDocument) {
        return null;
    }

    @Override
    public void flushQueryDocument(@NonNull QueryDocument queryDocument) {

    }
}