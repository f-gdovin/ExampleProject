package cz.muni.fi.xgdovin.service.config;

import cz.muni.fi.xgdovin.dao.config.DaoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("cz.muni.fi.xgdovin.service.service")
@Import(DaoConfiguration.class)
public class ServiceConfiguration {
}
