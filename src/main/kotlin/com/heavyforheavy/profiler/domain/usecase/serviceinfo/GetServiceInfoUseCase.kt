package com.heavyforheavy.profiler.domain.usecase.serviceinfo

import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.model.ServiceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository
) {
  suspend fun getServiceInfo(serviceInfoId: Int): ServiceInfo? = withContext(Dispatchers.IO) {
    serviceInfoRepository.getById(serviceInfoId)
  }
}