package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.*;

import java.util.List;

/**
 * GreetingController
 */

@RestController
public class GreetingController {
  @Autowired
  JdbcTemplate jdbctemplate;

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value="name") String name) {
    return new Greeting(5, "contenttest" + name);
  }
  
  @RequestMapping("/animal")
  public List<Animal> animal(@RequestParam(value="name") String name) {
    return jdbctemplate.query(
      "SELECT * FROM animals where name = ?", 
      new Object[]{ name},
      (rs, rowNum) -> new Animal(rs.getString("name"), rs.getLong("id")));
  }

}