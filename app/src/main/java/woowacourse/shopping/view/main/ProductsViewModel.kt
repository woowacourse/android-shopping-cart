package woowacourse.shopping.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.domain.Product

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
) : ViewModel() {
    private val _productsLiveData: MutableLiveData<Page<Product>> = MutableLiveData()
    val productsLiveData: LiveData<Page<Product>> get() = _productsLiveData
    val totalSize: Int get() = productsRepository.totalSize()

    fun requestProductsPage(requestPage: Int) {
        val pageRequest =
            PageRequest(
                pageSize = PAGE_SIZE,
                requestPage = requestPage,
            )
        val items = productsRepository.findAll(pageRequest)
        _productsLiveData.value = items
    }

    companion object {
        private const val PAGE_SIZE = 20
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val productsRepository =
                        (this[APPLICATION_KEY] as ShoppingCartApplication).productsRepository
                    ProductsViewModel(
                        productsRepository,
                    )
                }
            }
    }
}
