package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.page.Page

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val allProducts: List<Product> get() = shoppingCartRepository.findAll()
    private val _productsLiveData: MutableLiveData<Page<Product>> = MutableLiveData()

    val productsLiveData: LiveData<Page<Product>> get() = _productsLiveData

    fun removeProduct(product: Product) {
        val currentProductIndex = allProducts.indexOf(product)
        shoppingCartRepository.remove(product)
        val requestPage = pageNumberAfterRemoval(currentProductIndex)
        requestProductsPage(requestPage)
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
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val shoppingCartRepository = (this[APPLICATION_KEY] as ShoppingCartApplication).shoppingCartRepository
                    ShoppingCartViewModel(
                        shoppingCartRepository,
                    )
                }
            }
    }
}
