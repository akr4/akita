package net.physalis.akita;

import com.googlecode.flyway.core.Flyway;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  private Flyway flyway;

  @PostConstruct
  public void initLogger() {
    LogbackInitializer.init();
  }

  @PostConstruct
  public void migrateDb() {
    flyway.migrate();
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public DataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl("jdbc:postgresql://localhost/akita");
    ds.setUsername("akita");
    ds.setPassword("");
    ds.setPassword("");
    ds.setMaxActive(10);
    ds.setMaxIdle(5);
    ds.setInitialSize(5);
    ds.setValidationQuery("select current_timestamp");
    ds.setDefaultAutoCommit(false);
    return new TransactionAwareDataSourceProxy(ds);
  }

  @Bean
  public Flyway flyway(DataSource dataSource) {
    flyway = new Flyway();
    flyway.setDataSource(dataSource);
    return flyway;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public MdcFilter mdcFilter() {
    return new MdcFilter();
  }
}
