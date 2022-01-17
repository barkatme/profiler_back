package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asUser
import com.heavyforheavy.profiler.data.entity.asUserEntity
import com.heavyforheavy.profiler.data.tables.Users
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.exception.DatabaseException
import org.jetbrains.exposed.sql.*


class HerokuUserRepository : UserRepository {

  override suspend fun getByEmail(email: String): User? = dbQuery {
    Users.select { (Users.email eq email) }
      .mapNotNull { it.asUser() }
      .singleOrNull()
  }

  override suspend fun getById(id: Int): User? = dbQuery {
    Users.select { (Users.id eq id) }
      .mapNotNull { it.asUser() }
      .singleOrNull()
  }

  override suspend fun insert(user: User): User = dbQuery {
    Users.insert { table ->
      user.email?.also { table[email] = it }
      user.passwordHash?.also { table[passwordHash] = it }
      table[login] = user.login
      table[about] = user.about
      table[first_name] = user.firstName
      table[last_name] = user.lastName
      table[active] = user.isOnline
      table[token] = user.token
    }.resultedValues?.firstOrNull()?.asUser() ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun update(user: User): User {
    val entity = user.asUserEntity()
    dbQuery {
      Users.update(where = { Users.id eq user.id }) { table ->
        entity.email?.let { table[email] = it }
        entity.passwordHash?.let { table[passwordHash] = it }
        table[login] = entity.login
        table[about] = entity.about
        table[first_name] = entity.firstName
        table[last_name] = entity.lastName
        table[active] = entity.isOnline
        entity.token?.let { userToken -> table[token] = userToken }
      }
    }
    return getById(entity.id) ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun delete(user: User): Int = dbQuery {
    Users.deleteWhere {
      Users.id eq user.id
    }
  }

  override suspend fun delete(id: Int): Int = dbQuery {
    Users.deleteWhere {
      Users.id eq id
    }
  }

  override suspend fun count(): Int = dbQuery {
    Users.selectAll().count()
  }

  override suspend fun recent(): List<User> = dbQuery {
    Users.selectAll().orderBy(Users.date_registration, SortOrder.ASC).limit(10)
      .mapNotNull { it.asUserEntity().asUser() }
  }

  override suspend fun getByToken(token: String): User? = dbQuery {
    Users.select { (Users.token eq token) }
      .mapNotNull { it.asUser() }
      .singleOrNull()
  }
}