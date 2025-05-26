package woowacourse.shopping.data.cart.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts

interface CartRepository {
    fun getAll(callback: (Carts) -> Unit)

    fun insert(cart: Cart)

    fun insertAll(cart: Cart)

    fun delete(cart: Cart)

    fun deleteAll(cart: Cart)

    fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<Carts>

    fun getAllItemsSize(callback: (Int) -> Unit)

    fun getTotalQuantity(callback: (Int) -> Unit)
}
