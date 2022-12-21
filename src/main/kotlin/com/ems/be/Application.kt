package com.ems.be

import io.micronaut.context.ApplicationContextBuilder
import io.micronaut.context.ApplicationContextConfigurer
import io.micronaut.context.annotation.ContextConfigurer
import io.micronaut.runtime.Micronaut.*
import mu.KotlinLogging

@ContextConfigurer
class DefaultEnvironmentConfigurer : ApplicationContextConfigurer {
    override fun configure(builder: ApplicationContextBuilder) {
        val logger = KotlinLogging.logger {}
        logger.info("INITIALIZATION")
        builder.defaultEnvironments("dev");
    }
}

fun main(args: Array<String>) {
    run(*args)
}

