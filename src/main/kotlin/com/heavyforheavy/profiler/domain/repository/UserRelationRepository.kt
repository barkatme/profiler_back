package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.model.User
import com.heavyforheavy.profiler.model.getJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

interface UserRelationRepository {

  @Serializable
  data class ViewerResult(
    @SerialName("user") val user: User,
    @SerialName("lastViewDate") val lastViewDate: String
  )

  @Serializable
  data class SavedUserResult(
    @SerialName("user") val user: User,
    @SerialName("saveDate") val saveDate: String
  )

  suspend fun viewUser(userId: Int, viewedUserId: Int): Int
  suspend fun getViewers(
    userId: Int,
    search: String?,
    offset: Int?,
    limit: Int?
  ): List<ViewerResult>

  suspend fun deleteViewer(viewerId: Int, userId: Int): Int
  suspend fun deleteViewers(userId: Int): Int


  suspend fun saveUser(userId: Int, savedUserId: Int): Int
  suspend fun getSavedUsers(
    userId: Int,
    search: String?,
    offset: Int?,
    limit: Int?
  ): List<SavedUserResult>

  suspend fun deleteSavedUser(userId: Int, savedUserId: Int): Int
}

fun UserRelationRepository.ViewerResult.asString() =
  getJson().encodeToString(UserRelationRepository.ViewerResult.serializer(), this)

fun UserRelationRepository.ViewerResult.asJson() =
  getJson().encodeToJsonElement(UserRelationRepository.ViewerResult.serializer(), this)

fun List<UserRelationRepository.ViewerResult>.asJson() =
  getJson().encodeToJsonElement(
    ListSerializer(UserRelationRepository.ViewerResult.serializer()),
    this
  )

fun UserRelationRepository.SavedUserResult.asString() =
  getJson().encodeToString(UserRelationRepository.SavedUserResult.serializer(), this)

fun UserRelationRepository.SavedUserResult.asJson() =
  getJson().encodeToJsonElement(UserRelationRepository.SavedUserResult.serializer(), this)

fun List<UserRelationRepository.SavedUserResult>.toJson() =
  getJson().encodeToJsonElement(
    ListSerializer(UserRelationRepository.SavedUserResult.serializer()),
    this
  )