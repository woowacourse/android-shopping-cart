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


    override fun loadPagingProducts(offset: Int,pagingSize:Int): List<Product> {
        return productDao.findPagingProducts(offset, pagingSize)
    }

    override fun getProduct(productId: Long): Product {
        val product = productDao.findProductById(productId)
        return product ?: throw NoSuchDataException()
    }

    override fun addCartItem(product: Product): Long {
        return cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product))
    }

    override fun loadPagingCartItems(offset: Int,pagingSize:Int): List<CartItem> {
        return cartItemDao.findPagingCartItem(offset, pagingSize).map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItemDao.deleteCartItemById(itemId)
    }
}
