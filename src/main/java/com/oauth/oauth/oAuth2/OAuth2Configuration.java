package com.oauth.oauth.oAuth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
public class OAuth2Configuration {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<>();
        githubClientRegistration().ifPresent(registrations::add);
        googleClientRegistration().ifPresent(registrations::add);
        slackClientRegistration().ifPresent(registrations::add);

        if (registrations.isEmpty()) {
            log.error("No OAuth2 provider registered");
            throw new RuntimeException("At least one OAuth2 provider must be configured.");
        }

        return new InMemoryClientRegistrationRepository(registrations);
    }

    String slackClientId = "slackClientId";
    String slackClientSecret = "slackClientSecret";

    private Optional<ClientRegistration> slackClientRegistration() {

        return Optional.of(ClientRegistration.withRegistrationId("slack")
                .clientId(slackClientId)
                .clientSecret(slackClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/slack")
                .scope("identity.basic", "identity.email")
                .authorizationUri("https://slack.com/oauth/v2/authorize")
                .tokenUri("https://slack.com/api/oauth.v2.access")
                .userInfoUri("https://slack.com/api/users.identity")
                .userNameAttributeName("user.id")
                .clientName("Slack")
                .build());
    }

    String googleClientId = "googleClientId";
    String googleClientSecret = "googleClientSecret";


    private Optional<ClientRegistration> googleClientRegistration() {

        return Optional.of(ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/google")
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build());
    }

    String githubClientId = "githubClientId";
    String githubClientSecret = "githubClientSecret";

    private Optional<ClientRegistration> githubClientRegistration() {

        return Optional.of(ClientRegistration.withRegistrationId("github")
                .clientId(githubClientId)
                .clientSecret(githubClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .scope("read:user", "user:email")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("id")
                .clientName("GitHub")
                .build());
    }

}
