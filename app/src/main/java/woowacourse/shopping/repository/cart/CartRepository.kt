package woowacourse.shopping.repository.cart

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.CartItems
import woowacourse.shopping.domain.product.Product

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun addProduct(product: Product)

    suspend fun increase(productId:String)

    suspend fun decrease(productId:String)

    suspend fun remove(productId:String)
}
