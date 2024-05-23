package woowacourse.shopping.productDetail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.repository.DummyProductStore
import kotlin.properties.Delegates

class ProductDetailViewModel : ViewModel() {
    private val productStore = DummyProductStore()
    var productId by Delegates.notNull<Int>()

    val product: Product
        get() = productStore.findById(productId)


    val cartItem = ShoppingCart.cartItems.find { it.productId == productId }

    fun addProductToCart() {
        if (ShoppingCart.cartItems.any { it.productId == productId }) {
            addProductCount()
            return
        }
        ShoppingCart.addProductToCart(productId)
    }

    fun addProductCount() {
        ShoppingCart.addProductCount(productId)
    }

    fun subtractProductCount() {
        ShoppingCart.subtractProductCount(productId)
    }
}
