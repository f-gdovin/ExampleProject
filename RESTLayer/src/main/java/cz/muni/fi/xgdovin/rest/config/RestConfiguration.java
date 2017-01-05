package cz.muni.fi.xgdovin.rest.config;

import cz.muni.fi.xgdovin.service.config.ServiceConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("cz.muni.fi.xgdovin.rest.controller")
@Import(ServiceConfiguration.class)
public class RestConfiguration {
}
