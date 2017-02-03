package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception { 
    log.info("Creating tables");
    jdbcTemplate.execute("DROP TABLE IF EXISTS animals");
    jdbcTemplate.execute("CREATE TABLE animals( id serial, name varchar(255))");
    List<Object[]> animals = Arrays.asList("Cow", "Horse", "Cat").stream().map(name -> new String[]{name}).collect(Collectors.toList());
    jdbcTemplate.batchUpdate("INSERT INTO animals(name) values(?)", animals);
    log.info("Querying for animals where name = 'Cow':");
    jdbcTemplate.query(
      "SELECT * FROM animals where name = ?", 
      new Object[]{ "Cow" },
      (rs, rowNum) -> new Animal(rs.getString("name"), rs.getLong("id"))
    ).forEach(animal -> log.info(animal.getName()));
  }

}