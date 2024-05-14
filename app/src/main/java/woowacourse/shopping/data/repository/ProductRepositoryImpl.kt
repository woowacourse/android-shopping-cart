package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.db.product.ProductDao
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductRepositoryImpl(context: Context) : ProductRepository {
    private val productDao = ProductDao()
    private val cartItemDao = CartItemDatabase.getInstance(context).cartItemDao()

    override fun loadProducts(): List<Product> {
        return productDao.findAll()
    }

    override fun getProduct(productId: Long): Product {
        val product = productDao.findProductById(productId)
        return product ?: throw NoSuchDataException()
    }

    override fun addCartItem(cartItem: CartItem) {
        cartItemDao.saveCartItem(cartItem.toCartItemEntity())
    }

    override fun loadCartItems(): List<CartItem> {
        return cartItemDao.findAll().map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItemDao.deleteCartItemById(itemId)
    }
}
