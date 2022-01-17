package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asServiceInfo
import com.heavyforheavy.profiler.data.entity.asServiceInfoEntity
import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.model.ServiceInfo
import com.heavyforheavy.profiler.model.exception.DatabaseException
import org.jetbrains.exposed.sql.*

class HerokuServiceInfoRepository : ServiceInfoRepository {

  override suspend fun getAll(): List<ServiceInfo> = dbQuery {
    ServiceInfos.selectAll().map { it.asServiceInfo() }
  }

  override suspend fun getById(id: Int) = dbQuery {
    ServiceInfos.select { ServiceInfos.id eq id }.map { it.asServiceInfo() }.firstOrNull()
  }

  override suspend fun insert(serviceInfo: ServiceInfo): ServiceInfo = dbQuery {
    val entity = serviceInfo.asServiceInfoEntity()
    ServiceInfos.insert { table ->
      entity.link?.let { table[link] = it }
      entity.name?.let { table[name] = it }
      entity.image?.let { table[image] = it }
    }.resultedValues?.firstOrNull()?.asServiceInfo() ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun update(serviceInfo: ServiceInfo): ServiceInfo {
    val entity = serviceInfo.asServiceInfoEntity()
    dbQuery {
      ServiceInfos.update({ ServiceInfos.id eq entity.id }) { table ->
        table[id] = serviceInfo.id
        entity.link?.let { table[link] = it }
        entity.name?.let { table[name] = it }
        entity.image?.let { table[image] = it }
      }
    }
    return getById(entity.id) ?: throw DatabaseException.OperationFailed()
  }

  override suspend fun delete(serviceInfo: ServiceInfo) = dbQuery {
    ServiceInfos.deleteWhere { ServiceInfos.id eq serviceInfo.id }
  }

  override suspend fun delete(id: Int) = dbQuery {
    ServiceInfos.deleteWhere { ServiceInfos.id eq id }
  }

  override suspend fun deleteAll() = dbQuery {
    ServiceInfos.deleteAll()
  }

}