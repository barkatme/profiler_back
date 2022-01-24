package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.infrastructure.model.SavedUserResult
import com.heavyforheavy.profiler.infrastructure.model.ViewerResult

interface UserRelationRepository {

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