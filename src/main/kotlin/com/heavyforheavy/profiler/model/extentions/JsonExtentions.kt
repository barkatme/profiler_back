package com.heavyforheavy.profiler.model.extentions

import kotlinx.serialization.json.Json

@Suppress("unused")
fun Json.instance(pretty: Boolean = true) = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = pretty
}