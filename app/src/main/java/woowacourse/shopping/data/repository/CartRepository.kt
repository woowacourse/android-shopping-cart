package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.model.Cart

interface CartRepository {
    suspend fun getAll(): List<Cart>

    fun insert(cart: Cart)

    fun delete(cart: Cart)

    fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<Cart>>

    fun getAllItemsSize(): LiveData<Int>
}
