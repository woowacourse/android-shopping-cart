package woowacourse.shopping.data.cart.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts

interface CartRepository {
    suspend fun getAll(): Carts

    fun insert(cart: Cart)

    fun insertAll(cart: Cart)

    fun delete(cart: Cart)

    fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<Carts>

    fun getAllItemsSize(): LiveData<Int>

    fun getTotalQuantity(callback: (Int) -> Unit)
}
