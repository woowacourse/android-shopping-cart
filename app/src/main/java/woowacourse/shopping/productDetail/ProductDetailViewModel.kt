package woowacourse.shopping.productDetail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.FiveCartItemPagingStrategy
import woowacourse.shopping.db.Product
import woowacourse.shopping.repository.DummyProductStore
import woowacourse.shopping.repository.DummyShoppingCartItemRepository
import kotlin.properties.Delegates

class ProductDetailViewModel : ViewModel() {
    private val productStore = DummyProductStore()
    var productId by Delegates.notNull<Int>()

    val product: Product
        get() = productStore.findById(productId)

    fun addProductToCart() {
        DummyShoppingCartItemRepository(FiveCartItemPagingStrategy()).addCartItem(product)
    }
}
