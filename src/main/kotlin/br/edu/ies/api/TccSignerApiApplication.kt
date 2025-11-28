package br.edu.ies.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TccSignerApiApplication

fun main(args: Array<String>) {
    runApplication<TccSignerApiApplication>(*args)
}
