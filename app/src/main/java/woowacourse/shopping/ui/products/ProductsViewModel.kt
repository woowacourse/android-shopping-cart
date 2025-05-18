package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product

class ProductsViewModel(
    private val productsDummyRepository: ProductDummyRepositoryImpl = ProductDummyRepositoryImpl,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    private val _hasMoreProducts: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasMoreProducts: LiveData<Boolean> get() = _hasMoreProducts

    private val maxProductId: Int get() = products.value?.maxOfOrNull { it.id } ?: 0

    fun loadProducts(count: Int = SHOWN_PRODUCTS_COUNT) {
        val newProducts = productsDummyRepository.fetchProducts(count, maxProductId)
        _products.value = products.value?.plus(newProducts)
    }

    fun loadHasMoreProducts() {
        _hasMoreProducts.value = productsDummyRepository.fetchHasMoreProducts(maxProductId)
    }

    companion object {
        private const val SHOWN_PRODUCTS_COUNT: Int = 20
    }
}
