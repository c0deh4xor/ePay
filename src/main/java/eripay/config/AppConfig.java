/*package eripay.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Component
public class AppConfig {

  @Value("${spring.data.mongodb.uri}")
  String uri;

  @Value("${spring.data.mongodb.database}")
  String database;

  @Bean
  public MongoDbFactory mongoDbFactory() throws UnknownHostException {
    return new SimpleMongoDbFactory(new MongoClient(new MongoClientURI(uri)), database);
  }

  @Bean
  public MongoTemplate mongoTemplate() throws UnknownHostException {
    return new MongoTemplate(mongoDbFactory());
  }

}
*/