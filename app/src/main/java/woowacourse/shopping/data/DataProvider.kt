package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.cart.CartRepositoryRoomImpl
import woowacourse.shopping.data.product.ProductRepositoryMockImpl
import woowacourse.shopping.domain.cart.repository.CartRepository

object DataProvider {
    val productRepository = ProductRepositoryMockImpl()
    private var cartRepository: CartRepository? = null

    fun getCartRepository(context: Context): CartRepository =
        cartRepository ?: CartRepositoryRoomImpl(
            CartDatabase.getDatabase(context).cartDao(),
            productRepository,
        ).also { cartRepository = it }
}
