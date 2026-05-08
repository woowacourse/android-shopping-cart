package woowacourse.shopping.repository.cart

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItems
import woowacourse.shopping.domain.product.Product

class MockCartRepository : CartRepository {
    private var cart: Cart = Cart()

    override suspend fun getCart(): Cart = cart


    override suspend fun addProduct(product: Product) {
        cart = cart.addProduct(product)
    }

    override suspend fun increase(productId:String) {
        cart = cart.increase(productId)
    }

    override suspend fun decrease(productId:String){
        cart = cart.decrease(productId)
    }

    override suspend fun remove(productId:String){
        cart = cart.remove(productId)
    }
}
