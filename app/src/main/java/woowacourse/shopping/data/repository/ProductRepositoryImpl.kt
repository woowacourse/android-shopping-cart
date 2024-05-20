package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.db.product.ProductDao
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class ProductRepositoryImpl(context: Context) : ProductRepository {
    private val productDao = ProductDao()
    private val cartItemDao = CartItemDatabase.getInstance(context).cartItemDao()

    override fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        return productDao.findPagingProducts(offset, pagingSize)
    }

    override fun hasNextProductPage(
        offset: Int,
        pagingSize: Int,
    ): Boolean {
        return offset + pagingSize < productDao.getProductCount()
    }

    override fun getProduct(productId: Long): Product {
        val product = productDao.findProductById(productId)
        return product ?: throw NoSuchDataException()
    }

    override fun addCartItem(product: Product): Long {
        var addedCartItemId = ERROR_SAVE_DATA_ID
        thread {
            addedCartItemId = cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product))
        }.join()
        if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        return addedCartItemId
    }

    override fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem> {
        return cartItemDao.findPagingCartItem(offset, pagingSize).map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItemDao.deleteCartItemById(itemId)
    }

    override fun hasNextCartItemPage(
        currentPage: Int,
        itemsPerPage: Int,
    ): Boolean {
        var totalItemCount = 0
        thread { totalItemCount = cartItemDao.getItemCount() }.join()
        val totalPageCount = (totalItemCount + itemsPerPage - 1) / itemsPerPage

        return currentPage < totalPageCount
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
