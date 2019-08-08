package com.activiseek.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Activiseek.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    final private YelpApi yelpApi = new YelpApi();

    public YelpApi getYelpApi() {
        return yelpApi;
    }

    public class YelpApi {
        private String apiKey;
        private String basePath;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }

}
