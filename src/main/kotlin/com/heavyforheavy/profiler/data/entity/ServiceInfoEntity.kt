package com.heavyforheavy.profiler.data.entity

import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.model.ServiceInfo
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow


@Serializable
data class ServiceInfoEntity(
    val id: Int = 0,
    val link: String,
    val name: String,
    val image: String,
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