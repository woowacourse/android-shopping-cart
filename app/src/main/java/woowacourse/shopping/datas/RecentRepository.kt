package woowacourse.shopping.datas

import com.shopping.domain.RecentProduct

interface RecentRepository {
    fun getAll(): List<RecentProduct>

    fun getLatestProduct(): RecentProduct

    fun insert(recentProduct: RecentProduct)

    fun remove(recentProduct: RecentProduct)

    fun clear()

    fun isEmpty(): Boolean

    fun close()
}
