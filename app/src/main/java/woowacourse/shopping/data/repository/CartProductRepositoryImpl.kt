package woowacourse.shopping.data.repository

import com.shopping.domain.CartProduct
import com.shopping.repository.CartProductRepository
import woowacourse.shopping.data.db.CartProductDao

class CartProductRepositoryImpl(
    private val cartProductDao: CartProductDao
) : CartProductRepository {
    override fun getAll(): List<CartProduct> {
        return cartProductDao.getAll()
    }

    override fun insert(cartProduct: CartProduct) {
        cartProductDao.insert(cartProduct)
    }

    override fun remove(cartProduct: CartProduct) {
        cartProductDao.remove(cartProduct)
    }
}
