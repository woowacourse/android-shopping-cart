package woowacourse.shopping.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.page.Page

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
) : ViewModel() {
    private val allProducts: List<Product> = productsRepository.findAll()
    private val _productsLiveData: MutableLiveData<Page<Product>> = MutableLiveData()

    val totalSize: Int get() = allProducts.size
    val productsLiveData: LiveData<Page<Product>> get() = _productsLiveData

    fun requestProductsPage(requestPage: Int) {
        val page =
            Page.from(
                allProducts.toList(),
                requestPage,
                PAGE_SIZE,
            )
        _productsLiveData.value = page
    }

    companion object {
        private const val PAGE_SIZE = 20
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val productsRepository = (this[APPLICATION_KEY] as ShoppingCartApplication).productsRepository
                    ProductsViewModel(
                        productsRepository,
                    )
                }
            }
    }
}
