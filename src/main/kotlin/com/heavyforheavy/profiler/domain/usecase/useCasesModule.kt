package com.heavyforheavy.profiler.domain.usecase

import com.heavyforheavy.profiler.domain.usecase.auth.SignInUseCase
import com.heavyforheavy.profiler.domain.usecase.auth.SignOutUseCase
import com.heavyforheavy.profiler.domain.usecase.auth.SignUpUseCase
import com.heavyforheavy.profiler.domain.usecase.role.GetRolePermissionsUseCase
import com.heavyforheavy.profiler.domain.usecase.role.GetUserRolePermissionsUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.DeleteSavedUserUseCase
import com.heavyforheavy.profiler.domain.usecase.saveuser.GetSavedUsersUseCase
import com.heavyforheavy.profiler.domain.usecase.serviceinfo.*
import com.heavyforheavy.profiler.domain.usecase.saveuser.SaveUserUseCase
import com.heavyforheavy.profiler.domain.usecase.user.GetOtherUserUseCase
import com.heavyforheavy.profiler.domain.usecase.user.GetUserByEmailUseCase
import com.heavyforheavy.profiler.domain.usecase.user.GetUserByTokenUseCase
import com.heavyforheavy.profiler.domain.usecase.user.UpdateUserUseCase
import com.heavyforheavy.profiler.domain.usecase.userrole.GetUserRoleListUseCase
import com.heavyforheavy.profiler.domain.usecase.userservices.*
import com.heavyforheavy.profiler.domain.usecase.viewers.DeleteViewersByIdUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.DeleteViewersUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.GetViewersByIdUseCase
import com.heavyforheavy.profiler.domain.usecase.viewers.GetViewersUseCase
import org.koin.dsl.module

val useCasesModule = module {

    //auth
    single { SignInUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { SignOutUseCase(get()) }

    //user
    single { GetUserByEmailUseCase(get()) }
    single { GetOtherUserUseCase(get(), get()) }
    single { GetUserByTokenUseCase(get()) }
    single { UpdateUserUseCase(get()) }

    //rolePermissions
    single { GetUserRolePermissionsUseCase(get(), get()) }
    single { GetRolePermissionsUseCase(get()) }

    //viewers
    single { GetViewersUseCase(get(), get()) }
    single { GetViewersByIdUseCase(get(), get(), get(), get()) }
    single { DeleteViewersByIdUseCase(get(), get(), get(), get()) }
    single { DeleteViewersUseCase(get(), get(), get(), get()) }

    //serviceInfos
    single { GetServiceInfoUseCase(get()) }
    single { GetAllServiceInfoUseCase(get()) }
    single { AddServiceInfoUseCase(get(), get(), get(), get()) }
    single { UpdateServiceInfoUseCase(get(), get(), get(), get()) }
    single { DeleteServiceInfoUseCase(get(), get(), get(), get()) }
    single { DeleteServiceInfoByIdUseCase(get(), get(), get(), get()) }

    //save user
    single { SaveUserUseCase(get(), get()) }
    single { GetSavedUsersUseCase(get(), get()) }
    single { DeleteSavedUserUseCase(get(), get()) }

    //user services
    single { AddServiceUseCase(get(), get()) }
    single { GetServicesUseCase(get(), get()) }
    single { GetUserAndServicesUseCase(get(), get()) }
    single { UpdateServiceUseCase(get(), get()) }
    single { DeleteServiceUseCase(get()) }

    //others (admin/additional info requests)
    single { GetUserRoleListUseCase(get()) }
}