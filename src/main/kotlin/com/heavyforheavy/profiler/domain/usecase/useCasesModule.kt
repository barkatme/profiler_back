package com.heavyforheavy.profiler.domain.usecase

import com.heavyforheavy.profiler.domain.TokenManager
import com.heavyforheavy.profiler.domain.usecase.auth.SignInUseCase
import com.heavyforheavy.profiler.domain.usecase.auth.SignOutUseCase
import com.heavyforheavy.profiler.domain.usecase.auth.SignUpUseCase
import com.heavyforheavy.profiler.domain.usecase.email.SendEmailUseCase
import com.heavyforheavy.profiler.domain.usecase.role.GetRolePermissionsUseCase
import com.heavyforheavy.profiler.domain.usecase.role.GetUserPermissionsUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.DeleteSavedUserUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.GetSavedUsersUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.SaveUserUseCase
import com.heavyforheavy.profiler.domain.usecase.serviceinfo.*
import com.heavyforheavy.profiler.domain.usecase.user.*
import com.heavyforheavy.profiler.domain.usecase.userrole.GetUserRoleListUseCase
import com.heavyforheavy.profiler.domain.usecase.userservices.*
import com.heavyforheavy.profiler.domain.usecase.viewers.DeleteViewersByIdUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.DeleteViewersUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.GetViewersByIdUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.GetViewersUseCase
import org.koin.dsl.module

val useCasesModule = module {

  //auth
  single { TokenManager(get()) }
  factory { SignInUseCase(get(), get()) }
  factory { SignUpUseCase(get(), get()) }
  factory { SignOutUseCase(get(), get()) }

  factory { SendEmailUseCase() }

  //user
  factory { GetUserUseCase(get()) }
  factory { GetUserByEmailUseCase(get()) }
  factory { GetOtherUserUseCase(get(), get()) }
  factory { GetUserByTokenUseCase(get()) }
  factory { UpdateUserUseCase(get()) }
  factory { UpdateUserPasswordUseCase(get(), get()) }

  //rolePermissions
  factory { GetUserPermissionsUseCase(get(), get()) }
  factory { GetRolePermissionsUseCase(get()) }

  //viewers
  factory { GetViewersUseCase(get()) }
  factory { GetViewersByIdUseCase(get(), get(), get(), get()) }
  factory { DeleteViewersByIdUseCase(get(), get(), get(), get()) }
  factory { DeleteViewersUseCase(get(), get(), get(), get()) }

  //serviceInfos
  factory { GetServiceInfoUseCase(get()) }
  factory { GetAllServiceInfoUseCase(get()) }
  factory { AddServiceInfoUseCase(get(), get(), get(), get()) }
  factory { UpdateServiceInfoUseCase(get(), get(), get(), get()) }
  factory { DeleteServiceInfoUseCase(get(), get(), get(), get()) }

  //save user
  factory { SaveUserUseCase(get(), get()) }
  factory { GetSavedUsersUseCase(get()) }
  factory { DeleteSavedUserUseCase(get(), get()) }

  //user services
  factory { AddServiceUseCase(get()) }
  factory { GetServicesUseCase(get()) }
  factory { GetUserAndServicesUseCase(get(), get()) }
  factory { UpdateServiceUseCase(get(), get()) }
  factory { DeleteServiceUseCase(get()) }

  //others (admin/additional info requests)
  factory { GetUserRoleListUseCase(get()) }
}