package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asUserService
import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.data.tables.UserServices
import com.heavyforheavy.profiler.data.tables.Users
import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import com.heavyforheavy.profiler.model.UserService
import org.jetbrains.exposed.sql.*

class HerokuUserServiceRepository : UserServiceRepository {

    override suspend fun get(id: Int): UserService? = dbQuery {
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
            (UserServices.serviceId eq ServiceInfos.id) and (ServiceInfos.id inList getServiceIdListQuery(userId))
        }.map { it.asUserService() }
    }

    override suspend fun addService(userId: Int, serviceId: Int, link: String) = dbQuery {
        UserServices.insert {
            it[UserServices.userId] = userId
            it[UserServices.serviceId] = serviceId
            it[UserServices.link] = link
        } get UserServices.id
    }

    override suspend fun updateServiceLink(userService: UserService): Int = dbQuery {
        UserServices.update(where = {
            UserServices.id eq userService.id
        }) {
            it[link] = userService.userLink
        }
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