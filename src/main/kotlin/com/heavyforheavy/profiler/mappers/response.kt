package com.heavyforheavy.profiler.mappers

import com.heavyforheavy.profiler.domain.repository.UserRelationRepository
import com.heavyforheavy.profiler.domain.repository.asJson
import com.heavyforheavy.profiler.domain.repository.toJson
import com.heavyforheavy.profiler.domain.usecase.userservices.GetUserAndServicesUseCase
import com.heavyforheavy.profiler.domain.usecase.userservices.asJson
import com.heavyforheavy.profiler.model.*

fun User.response() = CustomResponse(this.asJson())
fun UserService.response() = CustomResponse(this.asJson())
fun GetUserAndServicesUseCase.UserAndServices.response() = CustomResponse(this.asJson())
fun Token.response() = CustomResponse(this.asJson())
fun Role.response() = CustomResponse(this.asJson())
fun Permission.response() = CustomResponse(this.asJson())
fun ServiceInfo.response() = CustomResponse(this.asJson())
fun UserRelationRepository.ViewerResult.response() = CustomResponse(this.asJson())
fun UserRelationRepository.SavedUserResult.response() = CustomResponse(this.asJson())

@JvmName("responseUsers")
fun List<User>.response() = CustomResponse(this.asJson())

@JvmName("responseUserServices")
fun List<UserService>.response() = CustomResponse(this.asJson())

@JvmName("responseUserRoles")
fun List<Role>.response() = CustomResponse(this.asJson())

@JvmName("responsePermissions")
fun List<Permission>.response() = CustomResponse(this.asJson())

@JvmName("responseServiceInfos")
fun List<ServiceInfo>.response() = CustomResponse(this.asJson())

@JvmName("userAndServices")
fun List<GetUserAndServicesUseCase.UserAndServices>.response() = CustomResponse(this.asJson())

@JvmName("responseViewedUsers")
fun List<UserRelationRepository.ViewerResult>.response() = CustomResponse(this.asJson())

@JvmName("responseSavedUsers")
fun List<UserRelationRepository.SavedUserResult>.response() = CustomResponse(this.toJson())