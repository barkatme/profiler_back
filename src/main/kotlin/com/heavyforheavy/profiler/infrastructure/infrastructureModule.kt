package com.heavyforheavy.profiler.infrastructure

import com.heavyforheavy.profiler.infrastructure.model.SimpleJWT
import org.koin.dsl.module

val infrastructureModule = module {
    factory { SimpleJWT("Bearer") }
}