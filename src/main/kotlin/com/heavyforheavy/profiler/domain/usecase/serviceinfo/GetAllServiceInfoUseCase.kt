package com.heavyforheavy.profiler.domain.usecase.serviceinfo

import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllServiceInfoUseCase(
  private val serviceInfoRepository: ServiceInfoRepository
) : UseCase<GetAllServiceInfoAction, GetAllServiceInfoResult> {

  override suspend fun invoke(action: GetAllServiceInfoAction): GetAllServiceInfoResult =
    withContext(Dispatchers.IO) {
      GetAllServiceInfoResult(serviceInfoRepository.getAll())
    }
}

object GetAllServiceInfoAction : Action

data class GetAllServiceInfoResult(val serviceInfos: List<ServiceInfo>) : Result