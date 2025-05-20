package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductsViewModel(
    productsRepository: ProductsRepository = DefaultProductsRepository(),
) : ViewModel() {
    private var allProducts: List<Product> = productsRepository.load()

    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _event: MutableSingleLiveData<Event> = MutableSingleLiveData()
    val event: SingleLiveData<Event> get() = _event

    private val lastProduct: Product? get() = products.value?.lastOrNull()
    val loadable: Boolean get() = allProducts.size > (products.value?.size ?: 0)

    init {
        updateProducts()
    }

    fun updateProducts() {
        runCatching {
            val startExclusive: Int = allProducts.indexOf(lastProduct)
            val lastExclusive: Int =
                (startExclusive + LOAD_PRODUCTS_SIZE).coerceAtMost(allProducts.size)
            allProducts.subList(startExclusive + 1, lastExclusive)
        }.onSuccess { newProducts: List<Product> ->
            _products.value = products.value?.plus(newProducts) ?: newProducts
        }.onFailure {
            _event.postValue(Event.UPDATE_PRODUCT_FAILURE)
        }
    }

    enum class Event {
        UPDATE_PRODUCT_FAILURE,
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
