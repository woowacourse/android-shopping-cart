package woowacourse.shopping.data.cart

import com.example.domain.CartProduct
import com.example.domain.Product
import com.example.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {

    override fun getAll(): List<CartProduct> {
        return cartDao.getAll()
    }

    override fun addProduct(product: Product) {
        cartDao.addColumn(product)
    }

    override fun deleteCartProduct(cartProduct: CartProduct) {
        cartDao.deleteColumn(cartProduct)
    }
}
