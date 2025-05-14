package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class ProductsViewModel(
    private val productsRepository: ProductsRepository = DefaultProductsRepository(),
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _event: MutableLiveData<ProductsEvent> = MutableLiveData()
    val event: LiveData<ProductsEvent> get() = _event

    private val _loadable: MutableLiveData<Boolean> = MutableLiveData()
    val loadable: LiveData<Boolean> get() = _loadable

    fun updateProducts() {
        thread {
            runCatching {
                productsRepository.load(
                    products.value?.lastOrNull()?.id,
                    LOAD_PRODUCTS_SIZE,
                )
            }.onSuccess { products: List<Product> ->
                _products.postValue(this.products.value?.plus(products))
                _loadable.postValue(productsRepository.loadable)
            }.onFailure {
                _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
            }
        }
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
