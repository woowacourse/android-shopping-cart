package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.page.Page

class ShoppingCartViewModel : ViewModel() {
    private val allProducts: Set<Product> get() = DummyShoppingCart.products.toSet()
    private val _productsLiveData: MutableLiveData<Page<Product>> = MutableLiveData()

    val productsLiveData: LiveData<Page<Product>> get() = _productsLiveData

    fun removeProduct(product: Product) {
        val currentProductIndex = allProducts.indexOf(product)
        DummyShoppingCart.products.remove(product)
        val pageNumber = pageNumberAfterRemoval(currentProductIndex)
        requestProductsPage(pageNumber)
    }

    fun requestProductsPage(requestPage: Int) {
        val page =
            Page.from(
                allProducts.toList(),
                requestPage,
                PAGE_SIZE,
            )
        _productsLiveData.value = page
    }

    private fun pageNumberAfterRemoval(index: Int): Int {
        val productsCount = allProducts.size
        val currentPageNumber = index / PAGE_SIZE
        val newPageNumber =
            if (productsCount % PAGE_SIZE == 0 && index == productsCount) {
                currentPageNumber - 1
            } else {
                currentPageNumber
            }
        return newPageNumber.coerceAtLeast(0)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
