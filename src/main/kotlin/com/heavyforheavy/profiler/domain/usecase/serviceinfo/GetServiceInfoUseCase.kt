package com.heavyforheavy.profiler.domain.usecase.serviceinfo

import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo
import com.heavyforheavy.profiler.infrastructure.model.exception.ServiceInfoException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository
) : UseCase<GetServiceInfoAction, GetServiceInfoResult> {

  override suspend fun invoke(action: GetServiceInfoAction): GetServiceInfoResult =
    withContext(Dispatchers.IO) {
      GetServiceInfoResult(
        serviceInfoRepository.getById(action.serviceInfoId)
          ?: throw ServiceInfoException.ServiceInfoNotFound()
      )
    }
}

data class GetServiceInfoAction(val serviceInfoId: Int) : Action

data class GetServiceInfoResult(val serviceInfo: ServiceInfo) : Result