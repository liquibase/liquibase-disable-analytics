package analytics

import liquibase.Scope
import liquibase.analytics.configuration.AnalyticsArgs
import spock.lang.Specification

class DisablingAnalyticsConfigurationValueProviderTest extends Specification {

    def "analytics are disabled even when purposefully enabled by the user"() {
        when:
        Map<String, Object> scopeVars = new HashMap<>()
        scopeVars.put(AnalyticsArgs.ENABLED.getKey(), true)
        scopeVars.put(AnalyticsArgs.DEV_OVERRIDE.getKey(), true)
        scopeVars.put(AnalyticsArgs.CONFIG_ENDPOINT_URL.getKey(), "https://config.liquibase.net/analytics.yaml")
        boolean enabled = Scope.child(scopeVars, () -> {
            return AnalyticsArgs.isAnalyticsEnabled()
        } as Scope.ScopedRunnerWithReturn)

        then:
        !enabled
    }
}
