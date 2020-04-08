package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    private final String dataSourceUrl = "jdbc:postgresql://localhost:5432/problemsdb";

    @Bean
    JdbcOperations jdbcOperations() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        //TODO read from credentials files (i'm too lazy to implement this)
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(this.dataSourceUrl);
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");
        basicDataSource.setInitialSize(2);
        return basicDataSource;
    }
}
