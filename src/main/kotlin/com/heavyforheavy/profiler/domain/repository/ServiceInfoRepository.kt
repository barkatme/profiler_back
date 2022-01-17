package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.model.ServiceInfo

interface ServiceInfoRepository {

    suspend fun getAll(): List<ServiceInfo>
    suspend fun getById(id: Int): ServiceInfo?
    suspend fun insert(serviceInfoEntity: ServiceInfo): Int
    suspend fun update(serviceInfoEntity: ServiceInfo): Int
    suspend fun delete(serviceInfoEntity: ServiceInfo): Int
    suspend fun delete(id: Int): Int
    suspend fun deleteAll(): Int

}