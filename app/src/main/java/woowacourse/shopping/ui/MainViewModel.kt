package woowacourse.shopping.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.livedata.Event
import kotlin.concurrent.thread

class MainViewModel(
    private val productRepository: ProductRepository = ProductRepository(),
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _onProductAdded = MutableLiveData<Event<Unit>>()
    val onProductAdded: LiveData<Event<Unit>> get() = _onProductAdded

    fun initProducts() {
        _products.value = emptyList()
        productRepository.initProducts()
        fetchProducts()
    }

    fun fetchProducts() {
        val newProducts = productRepository.getNextPage()
        _products.value = (products.value ?: emptyList()).plus(newProducts)
    }

    fun addCartProduct(product: Product) {
        thread {
            cartRepository.addCartProduct(product)
        }
        _onProductAdded.value = Event(Unit)
    }
}
