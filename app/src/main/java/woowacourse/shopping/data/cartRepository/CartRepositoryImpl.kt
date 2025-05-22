package woowacourse.shopping.data.cartRepository

import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

class CartRepositoryImpl private constructor(
    private val cartStorage: CartStorage,
) : CartRepository {
    override fun getAllProductsSize(onResult: (Int) -> Unit) = cartStorage.getAllProductsSize(onResult)

    override fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartItem>) -> Unit,
    ) = cartStorage.getProducts(limit, offset, onResult)

    override fun addProduct(product: Product) = cartStorage.addProduct(product)

    override fun deleteProduct(cartItemId: Long) = cartStorage.deleteProduct(cartItemId)

    companion object {
        private var instance: CartRepositoryImpl? = null

        fun initialize(cartStorage: CartStorage): CartRepositoryImpl = instance ?: CartRepositoryImpl(cartStorage).also { instance = it }
    }
}
