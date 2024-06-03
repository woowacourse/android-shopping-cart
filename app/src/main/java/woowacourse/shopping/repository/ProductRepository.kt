package woowacourse.shopping.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import woowacourse.shopping.db.ShoppingCartDatabase
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.service.MockWebService

class ProductRepository(context: Context) {
    private val productStore = MockWebService()
    private val cartItemDao = ShoppingCartDatabase
    private val recentlyViewedRepository = RecentlyViewedRepository(context)

    suspend fun getProductById(productId: Int): Product {
        return withContext(Dispatchers.IO) {
            productStore.findProductById(productId)
        }
    }

    suspend fun getCartItemById(productId: Int): CartItem {
        val cartItemEntity = withContext(Dispatchers.IO) {
            cartItemDao.getCartItemById(productId)
        }
        return cartItemEntity.let { CartItem(it.productId, it.quantity) }
    }

    suspend fun addProductToRecentlyViewed(productId: Int) {
        recentlyViewedRepository.addProduct(productId)
    }

    suspend fun addProductToCart(productId: Int) {
        ShoppingCartDatabase.addProductToCart(productId)
    }

    suspend fun addProductCount(productId: Int) {
        ShoppingCartDatabase.addProductCount(productId)
    }

    suspend fun subtractProductCount(productId: Int) {
        ShoppingCartDatabase.subtractProductCount(productId)
    }
}
