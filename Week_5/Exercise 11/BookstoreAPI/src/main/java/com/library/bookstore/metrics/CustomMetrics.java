package com.library.bookstore.metrics;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class CustomMetrics {

    private final MeterRegistry meterRegistry;

    // No need to use @Autowired here; Spring will handle it automatically
    public CustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        initializeMetrics();
    }

    private void initializeMetrics() {
        Gauge.builder("custom.metric", this, CustomMetrics::getCustomMetricValue)
             .description("A custom metric example")
             .register(meterRegistry);
    }

    private double getCustomMetricValue() {
        // Return your metric value here
        return 42.0; // Example value
    }
}
