package br.com.poc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class RequestAndReplyApplication

fun main(args: Array<String>) {
    runApplication<RequestAndReplyApplication>(*args)
}
