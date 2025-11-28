package br.edu.ies.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["br.edu.ies.api.infraestructure.repository"])
class MongoDbConfiguration {
}