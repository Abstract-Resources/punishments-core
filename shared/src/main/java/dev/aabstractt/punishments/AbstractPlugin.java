package dev.aabstractt.punishments;

import dev.aabstractt.punishments.datasource.DataSource;
import dev.aabstractt.punishments.datasource.DataSourceException;
import dev.aabstractt.punishments.datasource.impl.MongoDBDataSource;
import dev.aabstractt.punishments.datasource.impl.MySQLDataSource;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public final class AbstractPlugin {

    @Getter private final static @NonNull AbstractPlugin instance = new AbstractPlugin();
    @Getter private final static @NonNull Logger logger = LogManager.getLogger("AbsjustCore");

    private static @Nullable DataSource dataSource = null;

    private @Nullable PluginConfig pluginConfig;

    public boolean init(@NonNull PluginConfig pluginConfig) {
        String providerName = pluginConfig.getProviderName();

        if (!providerName.equalsIgnoreCase("mysql") && !providerName.equalsIgnoreCase("mongodb")) {
            logger.error("An error occurred while get the provider name... Please set a valid provider as 'MySQL' or 'MongoDB'");

            return false;
        }

        dataSource = providerName.equalsIgnoreCase("mysql") ? new MySQLDataSource() : new MongoDBDataSource();

        try {
            dataSource.init(pluginConfig.getProviderAuth());
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        // TODO: Initialize profile and punishment factory

        return true;
    }

    public static @NonNull DataSource getDataSource() {
        if (dataSource == null) {
            throw new DataSourceException("Plugin never was initialized... Please make sure that was initialized before call this method");
        }

        return dataSource;
    }

    public boolean isLoaded() {
        return this.pluginConfig != null;
    }
}