package com.heavyforheavy.profiler.domain.usecase.userservices

import com.heavyforheavy.profiler.domain.repository.UserServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteServiceUseCase(
    private val serviceRepository: UserServiceRepository
) {
    suspend fun deleteService(id: Int) = withContext(Dispatchers.IO) {
        serviceRepository.deleteUserService(id)
    }
}