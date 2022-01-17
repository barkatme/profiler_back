package com.heavyforheavy.profiler.domain.usecase.saveuser

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.UserRepository
import com.heavyforheavy.profiler.model.exception.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetSavedUsersUseCase(
    private val userRelationRepository: UserRelationRepository,
    private val userRepository: UserRepository
) {
    suspend fun getSavedUsers(
        userId: Int,
        search: String? = null,
        offset: Int? = null,
        limit: Int? = null
    ) = withContext(Dispatchers.IO) {
        userRelationRepository.getSavedUsers(userId, search, offset, limit)
    }

    suspend fun getSavedUsers(
        userEmail: String?,
        search: String? = null,
        offset: Int? = null,
        limit: Int? = null
    ) = withContext(Dispatchers.IO) {
        val user = userEmail?.let { userRepository.getByEmail(it) } ?: throw AuthException.InvalidEmail()
        getSavedUsers(user.id, search, offset, limit)
    }
}