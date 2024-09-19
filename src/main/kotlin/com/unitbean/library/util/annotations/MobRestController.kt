package com.unitbean.library.util.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@RestController
@RequestMapping
@Validated
annotation class MobRestController(
    @get:AliasFor(annotation = RequestMapping::class, value = "path")
    val value: String
)