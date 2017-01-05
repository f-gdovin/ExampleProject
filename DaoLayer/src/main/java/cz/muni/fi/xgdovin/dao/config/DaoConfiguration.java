package cz.muni.fi.xgdovin.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"cz.muni.fi.xgdovin.dao.dao",
                "cz.muni.fi.xgdovin.dao.domain",
                "cz.muni.fi.xgdovin.dao.utils"
})
@Import(EmbeddedDatabase.class)
public class DaoConfiguration {
}
