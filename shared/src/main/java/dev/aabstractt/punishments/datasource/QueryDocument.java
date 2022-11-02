package dev.aabstractt.punishments.datasource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor (access = AccessLevel.PRIVATE)
public final class QueryDocument {

    private final @Nullable String collection;
    private final @Nullable String statement;
    private final @NonNull Set<QueryDocumentValue> values;

    public @NonNull String getStringNonNull(@NonNull String key) {
        return (String) this.values.stream()
                .filter(queryDocumentValue -> queryDocumentValue.getKey().equalsIgnoreCase(key))
                .map(QueryDocumentValue::getValue)
                .filter(value -> value instanceof String)
                .findAny().orElseThrow(() -> new DataSourceException("Please make sure that value of " + key + " exists and is an String value"));
    }

    public boolean getBoolean(@NonNull String key) {
        return (boolean) this.values.stream()
                .filter(queryDocumentValue -> queryDocumentValue.getKey().equalsIgnoreCase(key))
                .map(QueryDocumentValue::getValue)
                .filter(value -> value instanceof Boolean)
                .findAny().orElse(false);
    }

    public int getInteger(@NonNull String key) {
        return (int) this.values.stream()
                .filter(queryDocumentValue -> queryDocumentValue.getKey().equalsIgnoreCase(key))
                .map(QueryDocumentValue::getValue)
                .filter(value -> value instanceof Integer)
                .findAny().orElseThrow(() -> new DataSourceException("Please make sure that value of " + key + " exists and is an Integer value"));
    }

    public static @NonNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private @Nullable String collection = null;
        private @Nullable String statement = null;

        private final @NonNull Set<QueryDocumentValue> values = new HashSet<>();

        public @NonNull Builder collection(@NonNull String collection) {
            this.collection = collection;

            return this;
        }

        public @NonNull Builder statement(@NonNull String statement) {
            this.statement = statement;

            return this;
        }

        public @NonNull Builder append(@NonNull String key, @Nullable Object value) {
            return this.append(-1, key, value);
        }

        public @NonNull Builder append(@NonNull Integer id, @NonNull String key, @Nullable Object value) {
            return this.append(QueryDocumentValue.builder()
                    .id(id)
                    .key(key)
                    .value(value)
                    .build()
            );
        }

        public @NonNull Builder append(@NonNull QueryDocumentValue queryDocumentValue) {
            this.values.add(queryDocumentValue);

            return this;
        }

        public @NonNull QueryDocument build() {
            if (this.collection == null || this.collection.isEmpty() || this.statement == null || this.statement.isEmpty()) {
                throw new DataSourceException("Please make sure that Statement and Collection will not have null or empty values");
            }

            return new QueryDocument(
                    this.collection,
                    this.statement,
                    this.values
            );
        }
    }

    @AllArgsConstructor @Getter @lombok.Builder
    private static final class QueryDocumentValue {

        private Integer id;
        private @NonNull String key;
        private @Nullable Object value;
    }
}