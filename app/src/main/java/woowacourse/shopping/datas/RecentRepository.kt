package woowacourse.shopping.datas

import woowacourse.shopping.uimodel.RecentProductUIModel

interface RecentRepository {
    fun getAll(): List<RecentProductUIModel>

    fun insert(recentProduct: RecentProductUIModel)

    fun remove(recentProduct: RecentProductUIModel)

    fun clear()

    fun isEmpty(): Boolean

    fun close()
}
