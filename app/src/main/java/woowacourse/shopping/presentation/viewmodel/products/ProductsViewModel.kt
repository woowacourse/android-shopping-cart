package woowacourse.shopping.presentation.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product

class ProductsViewModel(
    private val productsRepository: ProductRepository = ProductDummyRepositoryImpl,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    private val _isLoadable: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadable: LiveData<Boolean> get() = _isLoadable

    fun updateProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        val newProducts = productsRepository.fetchProducts(count, products.value?.lastOrNull()?.id ?: 0)
        _products.value = products.value?.plus(newProducts)
    }

    fun updateIsLoadable() {
        _isLoadable.value = productsRepository.fetchIsProductsLoadable(products.value?.lastOrNull()?.id ?: 0)
    }

    companion object {
        private const val SHOWN_PRODUCTS_COUNT: Int = 20
    }
}
