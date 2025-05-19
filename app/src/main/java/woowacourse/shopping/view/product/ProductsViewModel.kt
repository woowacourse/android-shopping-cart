package woowacourse.shopping.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.product.repository.ProductsRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData
import woowacourse.shopping.view.product.ProductsItem.LoadItem
import woowacourse.shopping.view.product.ProductsItem.ProductItem
import kotlin.concurrent.thread

class ProductsViewModel(
    private val productsRepository: ProductsRepository = DefaultProductsRepository(),
) : ViewModel() {
    private val _products: MutableLiveData<List<ProductsItem>> = MutableLiveData(emptyList())
    val products: LiveData<List<ProductsItem>> get() = _products

    private val _event: MutableSingleLiveData<Event> = MutableSingleLiveData()
    val event: SingleLiveData<Event> get() = _event

    private var loadable: Boolean = false

    fun updateProducts() {
        thread {
            runCatching {
                val lastProductId: Long? =
                    (products.value?.lastOrNull { it is ProductItem } as? ProductItem)?.product?.id
                productsRepository.load(
                    lastProductId,
                    LOAD_PRODUCTS_SIZE,
                )
            }.onSuccess { newProducts: List<Product> ->
                val products: List<ProductsItem> = products.value?.dropLast(1) ?: emptyList()
                loadable = productsRepository.loadable
                _products.postValue(
                    products + newProducts.map(::ProductItem) + LoadItem(loadable),
                )
            }.onFailure {
                it.printStackTrace()
                _event.postValue(Event.UPDATE_PRODUCT_FAILURE)
            }
        }
    }

    enum class Event {
        UPDATE_PRODUCT_FAILURE,
    }

    companion object {
        private const val LOAD_PRODUCTS_SIZE = 20
    }
}
