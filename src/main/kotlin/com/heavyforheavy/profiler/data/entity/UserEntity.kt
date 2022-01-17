package com.heavyforheavy.profiler.data.entity

import com.heavyforheavy.profiler.data.tables.Users
import com.heavyforheavy.profiler.model.User
import org.jetbrains.exposed.sql.ResultRow

data class UserEntity(
    val id: Int = 0,
    val login: String? = null,
    val isDeleted: Boolean = false,
    val firstName: String? = null,
    val lastName: String? = null,
    val about: String? = null,
    val email: String,
    val isOnline: Boolean? = null,
    val role: Int = 0,
    val dateRegistration: String? = null,

    val passwordHash: String = "",
    val token: String? = null
)

fun ResultRow.asUserEntity() = UserEntity(
    id = get(Users.id),
    login = get(Users.login),
    token = get(Users.token),
    firstName = get(Users.first_name),
    lastName = get(Users.last_name),
    about = get(Users.about),
    email = get(Users.email),
    isDeleted = get(Users.deleted),
    isOnline = get(Users.active),
    dateRegistration = get(Users.date_registration).toString(),
    role = get(Users.userRoleId),
    passwordHash = get(Users.passwordHash)
)

fun ResultRow.asUser() = asUserEntity().asUser()

fun UserEntity.asUser() = User(
    id = id,
    login = login,
    token = token,
    firstName = firstName,
    lastName = lastName,
    about = about,
    email = email,
    isDeleted = isDeleted,
    isOnline = isOnline,
    dateRegistration = dateRegistration,
    role = role,
    passwordHash = passwordHash
)

fun User.asUserEntity() = UserEntity(
    id = id,
    login = login,
    token = token,
    firstName = firstName,
    lastName = lastName,
    about = about,
    email = email,
    isDeleted = isDeleted,
    isOnline = isOnline,
    dateRegistration = dateRegistration,
    role = role,
    passwordHash = passwordHash
)