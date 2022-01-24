package com.heavyforheavy.profiler.domain.repository

import com.heavyforheavy.profiler.infrastructure.model.ServiceInfo

interface ServiceInfoRepository {

  suspend fun getAll(): List<ServiceInfo>
  suspend fun getById(id: Int): ServiceInfo?
  suspend fun insert(serviceInfo: ServiceInfo): ServiceInfo
  suspend fun update(serviceInfo: ServiceInfo): ServiceInfo
  suspend fun delete(serviceInfo: ServiceInfo): Int
  suspend fun delete(id: Int): Int
  suspend fun deleteAll(): Int

}