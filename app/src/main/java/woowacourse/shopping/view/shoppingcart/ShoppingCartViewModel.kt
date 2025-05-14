package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.page.Page
import kotlin.math.min

class ShoppingCartViewModel : ViewModel() {
    private var allProducts: Set<Product> = DummyShoppingCart.products.toSet()
    private val _productsLiveData: MutableLiveData<Page<Product>> = MutableLiveData()

    val productsLiveData: LiveData<Page<Product>> get() = _productsLiveData

    fun addProduct(product: Product) {
        allProducts = allProducts + product
    }

    fun removeProduct(product: Product) {
        val currentProductIndex = allProducts.indexOf(product)
        allProducts = allProducts - product
        requestProductsPage(currentProductIndex / PAGE_SIZE)
    }

    fun requestProductsPage(requestPage: Int) {
        val page = Page.from(
            allProducts.toList(),
            requestPage,
            PAGE_SIZE
        )
        _productsLiveData.value = page
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
