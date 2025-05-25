package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.entity.RecentProductEntity

interface RecentProductLocalDataSource {
    fun insert(productEntity: RecentProductEntity): Long

    fun getById(id: Long): RecentProductEntity

    fun getAll(): List<RecentProductEntity>

    fun deleteLastByCreatedDateTime()
}
