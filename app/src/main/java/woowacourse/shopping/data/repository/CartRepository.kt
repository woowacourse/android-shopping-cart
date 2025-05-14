package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.model.Goods

interface CartRepository {
    fun getAll(): LiveData<List<Goods>>

    fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    )

    fun delete(goods: Goods)
}
