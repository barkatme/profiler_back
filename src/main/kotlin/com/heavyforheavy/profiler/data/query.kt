package com.heavyforheavy.profiler.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.transactions.transaction


suspend fun <T> dbQuery(block: () -> T): T =
  withContext(Dispatchers.IO) {
    transaction { block() }
  }

fun Query.limitAndOffset(limit: Int? = null, offset: Int? = null) {
  if (limit != null && offset != null) {
    limit(limit, offset)
  } else if (limit != null) {
    limit(limit)
  }
}