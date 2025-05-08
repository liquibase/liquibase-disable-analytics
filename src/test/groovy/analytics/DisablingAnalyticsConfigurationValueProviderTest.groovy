package analytics

import liquibase.Scope
import liquibase.analytics.AnalyticsFactory
import liquibase.analytics.NoOpAnalyticsListener
import liquibase.analytics.configuration.AnalyticsArgs
import spock.lang.Specification

class DisablingAnalyticsConfigurationValueProviderTest extends Specification {

    def "analytics are disabled even when purposefully enabled by the user"() {
        when:
        Map<String, Object> scopeVars = new HashMap<>()
        scopeVars.put(AnalyticsArgs.ENABLED.getKey(), true)
        scopeVars.put(AnalyticsArgs.DEV_OVERRIDE.getKey(), true)
        scopeVars.put(AnalyticsArgs.CONFIG_ENDPOINT_URL.getKey(), "https://config.liquibase.net/analytics.yaml")
        def analyticsFactory = Scope.getCurrentScope().getSingleton(AnalyticsFactory.class)
        def listener = analyticsFactory.getListener()
        boolean enabled = Scope.child(scopeVars, () -> {
            return listener.isEnabled()
        } as Scope.ScopedRunnerWithReturn)

        then:
        !enabled
    }
}
