package woowacourse.shopping.productdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingCartRepository
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private lateinit var product: Product

    private val _productUi: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUi: LiveData<ProductUiModel> get() = _productUi

    private val _isAddSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isAddSuccess: LiveData<Boolean> get() = _isAddSuccess

    private val _addedItems: MutableLiveData<Set<Long>> = MutableLiveData()
    val addedItems: LiveData<Set<Long>> get() = _addedItems

    fun loadProductDetail(productId: Long) {
        runCatching {
            productRepository.productById(productId)
        }.onSuccess {
            product = it
            _productUi.value = it.toProductUiModel()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun addProductToCart() {
        runCatching {
            val cartItem = ShoppingCartItem(product)
            val userId = shoppingCartRepository.userId()
            val shoppingCart = shoppingCartRepository.shoppingCart(userId)
            shoppingCartRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
        }.onSuccess {
            val productId = requireNotNull(product.id)
            _addedItems.value = setOf(productId)
            _isAddSuccess.value = true
        }.onFailure {
            _isAddSuccess.value = false
        }
    }
}
