package org.liquibase.analytics;

import liquibase.Scope;
import liquibase.analytics.configuration.AnalyticsArgs;
import liquibase.command.CommandScope;
import liquibase.configuration.ConfigurationValueProvider;
import liquibase.configuration.ProvidedValue;

public class DisablingAnalyticsConfigurationValueProvider implements ConfigurationValueProvider {

    @Override
    public int getPrecedence() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ProvidedValue getProvidedValue(String... keyAndAliases) {
        if (keyAndAliases[0].equals(AnalyticsArgs.ENABLED.getKey())) {
            String message = "Analytics is being forcibly disabled because the liquibase-disable-analytics extension is " +
                    "on the classpath. If this is not your intention, remove the extension. If you do intend to disable " +
                    "analytics, note that you can disable analytics with the --analytics-enabled=false argument, or associated " +
                    "environment variable.";
            Scope.getCurrentScope().getLog(getClass()).severe(message);
            Scope.getCurrentScope().getUI().sendMessage(message);
            return new ProvidedValue(keyAndAliases[0], keyAndAliases[0], false, "Test override", this);
        }
        return null;
    }

    @Override
    public void validate(CommandScope commandScope) throws IllegalArgumentException {

    }
}
