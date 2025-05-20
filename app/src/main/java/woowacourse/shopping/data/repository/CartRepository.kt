package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.model.Goods

interface CartRepository {
    fun getAll(): LiveData<List<Goods>>

    fun insert(goods: Goods)

    fun delete(goods: Goods)

    fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<Goods>>

    fun getAllItemsSize(): LiveData<Int>
}
