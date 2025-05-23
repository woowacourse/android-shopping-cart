package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
    private val repository2: InventoryRepository,
) : ViewModel() {
    private val allProducts: Set<Product> get() = repository.getAll().toSet()
    private val _products = MutableLiveData<Page<Product>>()
    val products: LiveData<Page<Product>> get() = _products

    fun removeProduct(product: Product) {
        val currentProductIndex = allProducts.indexOf(product)
        repository.remove(product)
        val pageNumber = pageNumberAfterRemoval(currentProductIndex)
        requestPage(pageNumber)
    }

    fun requestPage(pageIndex: Int) {
        repository2.getPage(PAGE_SIZE, pageIndex) { page -> _products.postValue(page) }
    }

    fun requestPreviousPage() {
        val currentIndex = (_products.value?.pageIndex) ?: 0
        val previousIndex = (currentIndex - 1).coerceAtLeast(0)
        repository2.getPage(PAGE_SIZE, previousIndex) { page -> _products.postValue(page) }
    }

    fun requestNextPage() {
        val currentIndex = (_products.value?.pageIndex) ?: 0
        val nextIndex = currentIndex + 1
        repository2.getPage(PAGE_SIZE, nextIndex) { page -> _products.postValue(page) }
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
        fun createFactory(
            shoppingCartRepository: ShoppingCartRepository,
            repository: InventoryRepository,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        ShoppingCartViewModel(
                            shoppingCartRepository,
                            repository,
                        ) as T
                    )
                }
            }
        }
    }
}
