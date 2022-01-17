package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asUser
import com.heavyforheavy.profiler.data.limitAndOffset
import com.heavyforheavy.profiler.data.tables.SavedUsers
import com.heavyforheavy.profiler.data.tables.Users
import com.heavyforheavy.profiler.data.tables.ViewedUsers
import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.regexp
import org.joda.time.DateTime

class HerokuUserRelationRepository : UserRelationRepository {
    override suspend fun viewUser(userId: Int, viewedUserId: Int): Int? = dbQuery {
        ViewedUsers.update(where = { (ViewedUsers.userId eq userId) and (ViewedUsers.viewedUserId eq viewedUserId) }) {
            it[lastTime] = DateTime.now()
        }.takeIf { it == 0 }?.run {
            ViewedUsers.insert {
                it[ViewedUsers.userId] = userId
                it[ViewedUsers.viewedUserId] = viewedUserId
                it[lastTime] = DateTime.now()
            } get ViewedUsers.userId
        }
    }

    override suspend fun getViewers(
        userId: Int,
        search: String?,
        offset: Int?,
        limit: Int?
    ) = dbQuery {
        val query = (ViewedUsers.leftJoin(Users, { ViewedUsers.userId }, { id })).slice(
            ViewedUsers.lastTime,
            Users.id,
            Users.login,
            Users.token,
            Users.first_name,
            Users.last_name,
            Users.about,
            Users.email,
            Users.deleted,
            Users.active,
            Users.date_registration,
            Users.userRoleId,
            Users.passwordHash,
        ).select { ViewedUsers.viewedUserId eq userId }
        search?.let { query.adjustWhere { Users.first_name.regexp(it) or Users.last_name.regexp(it) } }
        query.limitAndOffset(limit, offset)
        query.map {
            UserRelationRepository.ViewerResult(
                it.asUser(),
                it[ViewedUsers.lastTime].toString()
            )
        }
    }

    override suspend fun deleteViewer(viewerId: Int, userId: Int): Int = dbQuery {
        ViewedUsers.deleteWhere { (ViewedUsers.userId eq userId) and (ViewedUsers.viewedUserId eq viewerId) }
    }

    override suspend fun deleteViewers(userId: Int): Int = dbQuery {
        ViewedUsers.deleteWhere { ViewedUsers.viewedUserId eq userId }
    }

    override suspend fun saveUser(userId: Int, savedUserId: Int): Int = dbQuery {
        SavedUsers.insertIgnore {
            it[SavedUsers.userId] = userId
            it[SavedUsers.savedUserId] = savedUserId
            it[createdAt] = DateTime.now()
        } get SavedUsers.id
    }

    override suspend fun getSavedUsers(
        userId: Int,
        search: String?,
        offset: Int?,
        limit: Int?
    ): List<UserRelationRepository.SavedUserResult> = dbQuery {
        val query = (SavedUsers.leftJoin(Users, { savedUserId }, { id })).slice(
            SavedUsers.createdAt,
            Users.id,
            Users.login,
            Users.token,
            Users.first_name,
            Users.last_name,
            Users.about,
            Users.email,
            Users.deleted,
            Users.active,
            Users.date_registration,
            Users.userRoleId,
            Users.passwordHash,
        ).select {
            SavedUsers.userId eq userId
        }
        search?.let { query.adjustWhere { Users.first_name.like("%$it%") or Users.last_name.like("%$it%") } }
        query.limitAndOffset(limit, offset)
        query.map {
            UserRelationRepository.SavedUserResult(
                it.asUser(),
                it[SavedUsers.createdAt].toString()
            )
        }
    }

    override suspend fun deleteSavedUser(userId: Int, savedUserId: Int): Int = dbQuery {
        SavedUsers.deleteWhere { (SavedUsers.userId eq userId) and (SavedUsers.savedUserId eq savedUserId) }
    }
}