package com.library.management.LibraryManagement.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.library.management.LibraryManagement.repository")
public class MongoConfiguration {


    @Value("${mongodb.database}")
    private String database;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${mongodb.port}")
    private int port;

    @Value("${mongodb.host}")
    private String databaseUrl;

    @Value("${mongodb.ssl.params:}")
    private String mongoParams;



    @Bean
    public MongoClient mongo() throws Exception {
        final ConnectionString connectionString = new ConnectionString(this.databaseUrl + ":" + this.port + "/" + this.database + this.mongoParams);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), this.database);
    }

    public Logger getLogger() {
        return logger;
    }

}
