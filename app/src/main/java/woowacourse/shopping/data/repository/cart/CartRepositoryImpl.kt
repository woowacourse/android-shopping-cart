package woowacourse.shopping.data.repository.cart

import woowacourse.shopping.data.source.cart.CartStorage
import woowacourse.shopping.data.source.products.ProductStorage
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

class CartRepositoryImpl private constructor(
    private val cartStorage: CartStorage,
    private val productStorage: ProductStorage,
) : CartRepository {
    override fun getAllProductsSize(onResult: (Int) -> Unit) = cartStorage.getAllProductsSize(onResult)

    override fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartItem>) -> Unit,
    ) {
        cartStorage.getProducts(limit, offset) { tableCartItems ->
            val products =
                tableCartItems.map {
                    CartItem(
                        productStorage.getProduct(it.productId),
                        it.count,
                    )
                }
            onResult(products)
        }
    }

    override fun addProduct(
        product: Product,
        count: Int,
    ) = cartStorage.addProduct(product, count)

    override fun deleteProduct(cartItemId: Long) = cartStorage.deleteProduct(cartItemId)

    override fun updateCartItem(cartItem: CartItem) = cartStorage.updateCartItem(cartItem)

    companion object {
        private var instance: CartRepositoryImpl? = null

        fun initialize(
            cartStorage: CartStorage,
            productStorage: ProductStorage,
        ): CartRepositoryImpl = instance ?: CartRepositoryImpl(cartStorage, productStorage).also { instance = it }
    }
}
