package vn.com.atomi.openbanking.authen.configuration;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Separate this to avoid cyclic dependency
 */
@Component
public class KeycloakResolverConfig {

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
