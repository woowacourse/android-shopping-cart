package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.db.product.ProductDao
import woowacourse.shopping.data.model.CartItemEntity
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

    override fun loadPagingProducts(offset: Int): List<Product> {
        return productDao.findPagingProducts(offset, PRODUCT_PAGING_SIZE)
    }

    override fun getProduct(productId: Long): Product {
        val product = productDao.findProductById(productId)
        return product ?: throw NoSuchDataException()
    }

    override fun addCartItem(product: Product): CartItem {
        val itemId = cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product))
        return CartItem(itemId, product)
    }

    override fun loadCartItems(): List<CartItem> {
        return cartItemDao.findAll().map { it.toCartItem() }
    }

    override fun loadPagingCartItems(offset: Int): List<CartItem> {
        return cartItemDao.findPagingCartItem(offset, CART_ITEM_PAGING_SIZE).map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItemDao.deleteCartItemById(itemId)
    }

    companion object {
        private const val PRODUCT_PAGING_SIZE = 20
        private const val CART_ITEM_PAGING_SIZE = 5
    }
}
