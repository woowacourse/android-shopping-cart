package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.cart.CartRepositoryRoomImpl
import woowacourse.shopping.data.database.CartDatabase
import woowacourse.shopping.data.product.ProductRepositoryMockImpl
import woowacourse.shopping.data.recentProduct.RecentProductRepositoryRoomImpl
import woowacourse.shopping.domain.RecentProductRepository
import woowacourse.shopping.domain.cart.repository.CartRepository

object DataProvider {
    val productRepository = ProductRepositoryMockImpl()
    private var cartRepository: CartRepository? = null
    private var recentProductRepository: RecentProductRepository? = null

    fun getCartRepository(context: Context): CartRepository =
        cartRepository ?: CartRepositoryRoomImpl(
            CartDatabase.getDatabase(context).cartDao(),
            productRepository,
        ).also { cartRepository = it }

    fun getRecentProductRepository(context: Context): RecentProductRepository =
        recentProductRepository ?: RecentProductRepositoryRoomImpl(
            CartDatabase.getDatabase(context).recentProductDao(),
            productRepository,
        ).also { recentProductRepository = it }
}
