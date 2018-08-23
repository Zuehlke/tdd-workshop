package org.zuhlke;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
public class NameServiceTestConfiguration {
    @Bean
    @Primary
    public NameService nameService() {
        return mock(NameService.class);
    }
}