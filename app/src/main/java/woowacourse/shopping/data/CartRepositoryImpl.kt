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
}
