package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductsViewModel(
    productsRepository: ProductsRepository = DefaultProductsRepository(),
) : ViewModel() {
    private val allProducts: List<Product> = productsRepository.load()

    private val products: MutableLiveData<List<Product>> = MutableLiveData()
    val productItems: LiveData<List<ProductsItem>> = products.map { it.toProductItems }

    private val loadable: Boolean get() = allProducts.size > (products.value?.size ?: 0)
    private val List<Product>.toProductItems: List<ProductsItem>
        get() = map(ProductsItem::ProductItem) + ProductsItem.LoadItem(loadable)

    private val _event: MutableSingleLiveData<Event> = MutableSingleLiveData()
    val event: SingleLiveData<Event> get() = _event

    init {
        updateProducts()
    }

    fun updateProducts() {
        runCatching {
            val lastProduct: Product? = products.value?.lastOrNull()
            val startExclusive: Int = allProducts.indexOf(lastProduct)
            val lastExclusive: Int =
                (startExclusive + LOAD_PRODUCTS_SIZE).coerceAtMost(allProducts.size)

            allProducts.subList(startExclusive + 1, lastExclusive)
        }.onSuccess { newProducts: List<Product> ->
            products.value = products.value?.plus(newProducts) ?: newProducts
        }.onFailure {
            _event.setValue(Event.UPDATE_PRODUCT_FAILURE)
        }
    }

    enum class Event {
        UPDATE_PRODUCT_FAILURE,
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
