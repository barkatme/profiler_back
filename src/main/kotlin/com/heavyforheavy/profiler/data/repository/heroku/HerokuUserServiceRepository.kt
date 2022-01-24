package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asUserService
import com.heavyforheavy.profiler.data.entity.asUserServiceEntity
import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.data.tables.UserServices
import com.heavyforheavy.profiler.data.tables.Users
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.infrastructure.model.UserService
import com.heavyforheavy.profiler.infrastructure.model.exception.DatabaseException
import org.jetbrains.exposed.sql.*

class HerokuUserServiceRepository : UserServiceRepository {

  override suspend fun getById(id: Int): UserService? = dbQuery {
    (UserServices innerJoin ServiceInfos).slice(
      UserServices.id,
      UserServices.userId,
      UserServices.link,
      ServiceInfos.id,
      ServiceInfos.image,
      ServiceInfos.link,
      ServiceInfos.name
    ).select { (UserServices.serviceId eq ServiceInfos.id) and (UserServices.id eq id) }
      .mapNotNull { it.asUserService() }
      .singleOrNull()
  }

  override suspend fun getByEmail(email: String): List<UserService> = dbQuery {
    (UserServices innerJoin ServiceInfos).slice(
      UserServices.id,
      UserServices.userId,
      UserServices.link,
      ServiceInfos.id,
      ServiceInfos.image,
      ServiceInfos.link,
      ServiceInfos.name
    ).select {
      (UserServices.serviceId eq ServiceInfos.id) and (ServiceInfos.id inList getServiceIdListQuery(
        Users.select { Users.email eq email }.limit(1).first()[Users.id]
      ))
    }.map { it.asUserService() }
  }

  override suspend fun getByUserId(userId: Int): List<UserService> = dbQuery {
    (UserServices innerJoin ServiceInfos).slice(
      UserServices.id,
      UserServices.userId,
      UserServices.link,
      ServiceInfos.id,
      ServiceInfos.image,
      ServiceInfos.link,
      ServiceInfos.name
    ).select {
      (UserServices.serviceId eq ServiceInfos.id) and (ServiceInfos.id inList getServiceIdListQuery(
        userId
      ))
    }.map { it.asUserService() }
  }

  override suspend fun addService(userId: Int, serviceId: Int, link: String): UserService =
    dbQuery {
      UserServices.insert { table ->
        table[UserServices.userId] = userId
        table[UserServices.serviceId] = serviceId
        table[UserServices.link] = link
      }.resultedValues?.firstOrNull()?.asUserService() ?: throw DatabaseException.OperationFailed()
    }

  override suspend fun updateServiceLink(userService: UserService): UserService {
    val entity = userService.asUserServiceEntity()
    dbQuery {
      UserServices.update(where = {
        UserServices.id eq entity.id
      }) { table ->
        entity.userLink?.let { table[link] = it }
      }
    }
    return getById(entity.id) ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun deleteUserService(id: Int): Int = dbQuery {
    UserServices.deleteWhere {
      UserServices.id eq id
    }
  }

  private fun getServiceIdListQuery(userId: Int): List<Int> =
    UserServices.select {
      UserServices.userId eq userId
    }.map { it[UserServices.serviceId] }
}