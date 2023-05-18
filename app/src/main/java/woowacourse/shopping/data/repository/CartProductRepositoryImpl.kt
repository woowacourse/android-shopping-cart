package woowacourse.shopping.data.repository

import com.shopping.domain.CartProduct
import com.shopping.domain.Count
import com.shopping.repository.CartProductRepository
import woowacourse.shopping.data.db.CartProductDao

class CartProductRepositoryImpl(
    private val cartProductDao: CartProductDao
) : CartProductRepository {
    private val cartProducts
        get() = getAll()

    override fun getAll(): List<CartProduct> {
        return cartProductDao.getAll()
    }

    override fun loadCartProducts(index: Pair<Int, Int>): List<CartProduct> {
        if (index.first >= cartProducts.size) {
            return emptyList()
        }

        return cartProducts.subList(index.first, minOf(index.second, cartProducts.size))
    }

    override fun update(cartProduct: CartProduct, count: Count) {
        cartProductDao.update(cartProduct, count)
    }

    override fun insert(cartProduct: CartProduct) {
        cartProductDao.insert(cartProduct)
    }

    override fun remove(cartProduct: CartProduct) {
        cartProductDao.remove(cartProduct)
    }
}
