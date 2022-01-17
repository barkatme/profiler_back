package com.heavyforheavy.profiler.data

import com.heavyforheavy.profiler.data.repository.heroku.*
import com.heavyforheavy.profiler.domain.repository.*
import org.koin.dsl.module

val dataModule = module {

    //heroku repositories
    single<ServiceInfoRepository> { HerokuServiceInfoRepository() }
    single<UserRelationRepository> { HerokuUserRelationRepository() }
    single<UserRepository>{ HerokuUserRepository() }
    single<UserServiceRepository>{ HerokuUserServiceRepository() }
    single<RoleRepository>{ HerokuUserRoleRepository() }
    single<PermissionRepository>{ HerokuPermissionRepository() }

}