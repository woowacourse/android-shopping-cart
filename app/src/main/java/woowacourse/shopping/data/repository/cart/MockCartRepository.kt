package woowacourse.shopping.data.repository.cart

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository

class MockCartRepository : CartRepository {
    private val _cartFlow = MutableStateFlow(Cart())
    override val cartFlow: StateFlow<Cart> = _cartFlow.asStateFlow()


    override suspend fun addProduct(product: Product, quantity: Quantity) {
        _cartFlow.update {it.addProduct(product, quantity)}
    }

    override suspend fun increase(productId:String) {
        _cartFlow.update{it.increase(productId)}
    }

    override suspend fun decrease(productId:String){
        _cartFlow.update{it.decrease(productId)}
    }

    override suspend fun remove(productId:String){
        _cartFlow.update{it.remove(productId)}
    }
}
