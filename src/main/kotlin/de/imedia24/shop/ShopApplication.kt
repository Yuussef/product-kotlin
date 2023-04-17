package de.imedia24.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ShopApplication

fun main(args: Array<String>) {
	runApplication<ShopApplication>(*args)
}
