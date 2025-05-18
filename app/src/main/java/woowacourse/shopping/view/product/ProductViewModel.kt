package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.productsRepository.ProductRepository
import woowacourse.shopping.domain.Product

class ProductViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    fun fetchData() {
        val newProducts = productRepository.getProducts(LIMIT_COUNT)
        _products.value = (_products.value ?: emptyList()).plus(newProducts)
    }

    companion object {
        private const val LIMIT_COUNT = 20

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val productRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).productRepository
                    ProductViewModel(
                        productRepository = productRepository,
                    )
                }
            }
    }
}
