package woowacourse.shopping.repository.cart

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product

class MockCartRepository : CartRepository {
    private val _cartFlow = MutableStateFlow(Cart())
    override val cartFlow: StateFlow<Cart> = _cartFlow.asStateFlow()


    override suspend fun addProduct(product: Product) {
        _cartFlow.update{it.addProduct(product)}
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
