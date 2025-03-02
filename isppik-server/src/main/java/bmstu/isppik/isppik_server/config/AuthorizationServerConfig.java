package bmstu.isppik.isppik_server.config;

import bmstu.isppik.isppik_server.repository.users.OAuthClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import java.util.List   ;
import java.util.stream.Collectors;

@Configuration
public class AuthorizationServerConfig {


    @Bean
    public RegisteredClientRepository registeredClientRepository(OAuthClientRepository clientRepository) {
        List<RegisteredClient> clients = clientRepository.findAll().stream()
                .map(client -> RegisteredClient.withId(client.getClientId())
                        .clientId(client.getClientId())
                        .clientSecret(client.getClientSecret())
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .authorizationGrantType(AuthorizationGrantType.)
                        .scopes(strings -> {
                            strings.addAll(List.of(client.getScopes().split(";")));
                        })
                        .build())
                .collect(Collectors.toList());

        return new InMemoryRegisteredClientRepository(clients);
    }

    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8080")
                .build();
    }

    @Bean
    public OAuth2AuthorizationServerConfigurer authorizationServerConfigurer() {
        return new OAuth2AuthorizationServerConfigurer();
    }
}
