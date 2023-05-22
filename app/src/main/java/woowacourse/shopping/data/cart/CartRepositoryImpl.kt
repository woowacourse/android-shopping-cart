package woowacourse.shopping.data.cart

import com.example.domain.CartProduct
import com.example.domain.repository.CartRepository
import woowacourse.shopping.data.product.MockProductRemoteService

class CartRepositoryImpl(
    private val productMockProductRemoteService: MockProductRemoteService,
    private val cartDao: CartDao,
) : CartRepository {

    override fun getAll(): List<CartProduct> {
        return cartDao.getAll()
    }

    override fun getCartProduct(productId: Int): CartProduct? {
        return cartDao.getCartProduct(productId)
    }

    // todo 임시 데이터 사용, 수정 필요
    override fun addProduct(productId: Int, count: Int) {
        productMockProductRemoteService.requestProduct(
            productId = productId.toLong(),
            onSuccess = { if (it != null) cartDao.addColumn(it, count) },
            onFailure = {}
        )
    }

    override fun deleteCartProduct(productId: Int) {
        cartDao.deleteColumn(productId)
    }

    override fun updateCartProductCount(productId: Int, count: Int) {
        cartDao.updateCartProductCount(productId, count)
    }

    override fun updateCartProductChecked(productId: Int, checked: Boolean) {
        cartDao.updateCartProductChecked(productId, checked)
    }
}
