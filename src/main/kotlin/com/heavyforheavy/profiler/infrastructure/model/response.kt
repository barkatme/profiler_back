package com.heavyforheavy.profiler.infrastructure.model

fun OperationResult.response() = CustomResponse(this.asJson())
fun User.response() = CustomResponse(this.asJson())
fun UserService.response() = CustomResponse(this.asJson())
fun UserAndServices.response() = CustomResponse(this.asJson())
fun Token.response() = CustomResponse(this.asJson())
fun Role.response() = CustomResponse(this.asJson())
fun Permission.response() = CustomResponse(this.asJson())
fun ServiceInfo.response() = CustomResponse(this.asJson())
fun ViewerResult.response() = CustomResponse(this.asJson())
fun SavedUserResult.response() = CustomResponse(this.asJson())

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
fun List<UserAndServices>.response() = CustomResponse(this.asJson())

@JvmName("responseViewedUsers")
fun List<ViewerResult>.response() = CustomResponse(this.asJson())

@JvmName("responseSavedUsers")
fun List<SavedUserResult>.response() = CustomResponse(this.toJson())