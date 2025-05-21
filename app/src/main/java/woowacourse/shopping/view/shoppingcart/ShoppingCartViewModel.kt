package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) : ViewModel() {
    private val allProducts: Set<Product> get() = repository.getAll().toSet()
    private val _products = MutableLiveData<Page<Product>>()
    val products: LiveData<Page<Product>> get() = _products

    fun removeProduct(product: Product) {
        val currentProductIndex = allProducts.indexOf(product)
        repository.remove(product)
        val pageNumber = pageNumberAfterRemoval(currentProductIndex)
        requestProductsPage(pageNumber)
    }

    fun requestProductsPage(pageIndex: Int) {
        _products.value = repository.getPage(PAGE_SIZE, pageIndex)
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

        @Suppress("UNCHECKED_CAST")
        fun createFactory(shoppingCartRepository: ShoppingCartRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (ShoppingCartViewModel(shoppingCartRepository) as T)
                }
            }
        }
    }
}
