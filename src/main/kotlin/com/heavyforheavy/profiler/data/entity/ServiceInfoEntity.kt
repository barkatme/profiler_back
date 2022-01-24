package com.heavyforheavy.profiler.data.entity

import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo
import org.jetbrains.exposed.sql.ResultRow


data class ServiceInfoEntity(
  val id: Int = 0,
  val link: String? = null,
  val name: String? = null,
  val image: String? = null,
)

fun ResultRow.asServiceInfoEntity() = ServiceInfoEntity(
  id = get(ServiceInfos.id),
  link = get(ServiceInfos.link),
  name = get(ServiceInfos.name),
  image = get(ServiceInfos.image)
)

fun ResultRow.asServiceInfo() = asServiceInfoEntity().asServiceInfo()

fun ServiceInfoEntity.asServiceInfo() = ServiceInfo(
  id = id,
  link = link,
  name = name,
  image = image
)

fun ServiceInfo.asServiceInfoEntity() = ServiceInfoEntity(
  id = id,
  link = link,
  name = name,
  image = image
)