package woowacourse.shopping.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    val imageUrl: LiveData<String> = _product.map { it.imageUrl }

    private val _event: MutableLiveData<ProductDetailEvent> = MutableLiveData()
    val event: LiveData<ProductDetailEvent> get() = _event

    fun updateProduct(product: Product) {
        _product.value = product
    }

    fun addToShoppingCart() {
        thread {
            runCatching {
                val product = requireNotNull(product.value) { "product.value가 null입니다." }
                shoppingCartRepository.add(product)
            }.onSuccess {
                _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS)
            }.onFailure {
                _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
            }
        }
    }
}
