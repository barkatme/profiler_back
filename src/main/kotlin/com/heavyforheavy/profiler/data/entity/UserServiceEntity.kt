package com.heavyforheavy.profiler.data.entity

import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.data.tables.UserServices
import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo
import com.heavyforheavy.profiler.infrastructure.model.UserService
import org.jetbrains.exposed.sql.ResultRow

data class UserServiceEntity(
  val id: Int = 0,
  val serviceInfoId: Int = 0,
  val link: String? = null,
  val name: String? = null,
  val image: String? = null,
  val userId: Int? = null,
  val userLink: String? = null
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