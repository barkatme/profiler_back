package com.heavyforheavy.profiler.data.entity

import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.data.tables.UserServices
import com.heavyforheavy.profiler.model.ServiceInfo
import com.heavyforheavy.profiler.model.UserService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class UserServiceEntity(
    @SerialName("id") val id: Int = 0,
    @SerialName("serviceInfoId") val serviceInfoId: Int = 0,
    @SerialName("link") val link: String,
    @SerialName("name") val name: String,
    @SerialName("image") val image: String,
    @SerialName("userId") val userId: Int,
    @SerialName("user_link") val userLink: String,
)

fun ResultRow.asUserServiceEntity() = UserServiceEntity(
    id = get(UserServices.id),
    serviceInfoId = get(ServiceInfos.id),
    link = get(ServiceInfos.link),
    name = get(ServiceInfos.name),
    image = get(ServiceInfos.image),
    userId = get(UserServices.userId),
    userLink = get(UserServices.link)
)

fun ResultRow.asUserService() = asUserServiceEntity().asUserService()

fun UserServiceEntity.asUserService() = UserService(
    id = id,
    serviceInfo = ServiceInfo(
        id = serviceInfoId,
        link = link,
        name = name,
        image = image
    ),
    userId = userId,
    userLink = userLink
)

fun UserService.asUserServiceEntity() = UserServiceEntity(
    id = id,
    serviceInfoId = serviceInfo.id,
    link = serviceInfo.link,
    name = serviceInfo.name,
    image = serviceInfo.image,
    userId = userId,
    userLink = userLink
)