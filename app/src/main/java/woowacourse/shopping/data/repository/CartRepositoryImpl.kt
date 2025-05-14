package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.CartEntity

class CartRepositoryImpl(
    private val cartDatabase: CartDatabase,
) : CartRepository {
    override fun getAll(): LiveData<List<CartEntity>> = cartDatabase.cartDao().getAll()
}
