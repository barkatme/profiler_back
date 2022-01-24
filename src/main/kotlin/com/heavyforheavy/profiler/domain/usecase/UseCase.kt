package com.heavyforheavy.profiler.domain.usecase

interface UseCase<A : Action, R : Result> {
  suspend fun invoke(action: A): R
}