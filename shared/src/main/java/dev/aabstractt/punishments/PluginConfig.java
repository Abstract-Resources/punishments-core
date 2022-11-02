package dev.aabstractt.punishments;

import dev.aabstractt.punishments.datasource.QueryDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

@AllArgsConstructor @Getter
public final class PluginConfig {

    private final @NonNull String providerName;
    private final Map<String, Object> providerAuth;

    public @NonNull QueryDocument getProviderAuth() {
        return QueryDocument.builder()
                .append("address", this.providerAuth.get("address"))
                .append("username", this.providerAuth.get("username"))
                .append("password", this.providerAuth.get("password"))
                .append("dbname", this.providerAuth.get("dbname"))
                .build();
    }
}