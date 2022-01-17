package com.heavyforheavy.profiler.data.repository.heroku

import com.heavyforheavy.profiler.data.dbQuery
import com.heavyforheavy.profiler.data.entity.asServiceInfo
import com.heavyforheavy.profiler.data.tables.ServiceInfos
import com.heavyforheavy.profiler.domain.repository.ServiceInfoRepository
import com.heavyforheavy.profiler.model.ServiceInfo
import org.jetbrains.exposed.sql.*

class HerokuServiceInfoRepository : ServiceInfoRepository {

    override suspend fun getAll(): List<ServiceInfo> = dbQuery {
        ServiceInfos.selectAll().map { it.asServiceInfo() }
    }

    override suspend fun getById(id: Int) = dbQuery {
        ServiceInfos.select { ServiceInfos.id eq id }.map { it.asServiceInfo() }.firstOrNull()
    }

    override suspend fun insert(serviceInfoEntity: ServiceInfo) = dbQuery {
        ServiceInfos.insert {
            it[link] = serviceInfoEntity.link
            it[name] = serviceInfoEntity.name
            it[image] = serviceInfoEntity.image
        } get ServiceInfos.id
    }

    override suspend fun update(serviceInfoEntity: ServiceInfo) = dbQuery {
        ServiceInfos.update({ ServiceInfos.id eq serviceInfoEntity.id }) {
            it[id] = serviceInfoEntity.id
            it[link] = serviceInfoEntity.link
            it[name] = serviceInfoEntity.name
            it[image] = serviceInfoEntity.image
        }
    }

    override suspend fun delete(serviceInfoEntity: ServiceInfo) = dbQuery {
        ServiceInfos.deleteWhere { ServiceInfos.id eq serviceInfoEntity.id }
    }

    override suspend fun delete(id: Int) = dbQuery {
        ServiceInfos.deleteWhere { ServiceInfos.id eq id }
    }

    override suspend fun deleteAll() = dbQuery {
        ServiceInfos.deleteAll()
    }

}