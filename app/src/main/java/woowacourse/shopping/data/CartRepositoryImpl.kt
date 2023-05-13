package woowacourse.shopping.data

import com.example.domain.model.CartProduct
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import woowacourse.shopping.data.sql.cart.CartDao

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {
    override fun getAll(): List<CartProduct> {
        return cartDao.selectAll()
    }

    override fun addProduct(product: Product) {
        cartDao.insertProduct(product)
    }

    override fun deleteProduct(cartProduct: CartProduct) {
        cartDao.deleteCartProduct(cartProduct)
    }

    override fun getPreviousProducts(size: Int, topId: Long): List<CartProduct> {
        val all = getAll()
        val lower = all.filter { it.cartId < topId }.takeLast(size)
        if (lower.size != size) return all.take(size)
        return lower
    }

    override fun getNextProducts(size: Int, bottomId: Long): List<CartProduct> {
        return getAll().asSequence().filter { it.cartId > bottomId }.take(size).toList()
    }

    override fun getProducts(size: Int): List<CartProduct> {
        return getAll().take(size)
    }

    override fun getProductsFromId(size: Int, startId: Long): List<CartProduct> {
        return getAll().asSequence().filter { it.cartId >= startId }.take(size).toList()
    }

    override fun getProductsByRange(startIndex: Int, endIndex: Int): List<CartProduct> {
        return getAll().subList(startIndex, endIndex)
    }
}
