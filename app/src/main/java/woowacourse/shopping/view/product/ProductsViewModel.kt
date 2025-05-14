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
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val _event: MutableLiveData<ProductsEvent> = MutableLiveData()
    val event: LiveData<ProductsEvent> get() = _event

    fun updateProducts() {
        thread {
            runCatching {
                productsRepository.products
            }.onSuccess { products: List<Product> ->
                _products.postValue(products)
            }.onFailure {
                _event.postValue(ProductsEvent.UPDATE_PRODUCT_FAILURE)
            }
        }
    }
}
