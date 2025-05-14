package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product

class ProductDetailViewModel : ViewModel() {
    fun addProduct(product: Product) {
        DummyShoppingCart.products.add(product)
    }
}
