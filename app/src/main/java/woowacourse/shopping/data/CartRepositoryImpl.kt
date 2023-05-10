package woowacourse.shopping.data

import com.example.domain.model.CartItem
import com.example.domain.model.Product
import com.example.domain.repository.CartRepository
import woowacourse.shopping.data.sql.CartDao

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {
    override fun getAll(): List<CartItem> {
        return cartDao.selectAll()
    }

    override fun addProduct(product: Product) {
        cartDao.insertProduct(product)
    }

    override fun deleteProduct(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }
}