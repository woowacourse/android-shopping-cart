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
    private val _productUi: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUi: LiveData<ProductUiModel> get() = _productUi

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _isAddSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isAddSuccess: LiveData<Boolean> get() = _isAddSuccess

    private val _addedItems: MutableLiveData<Set<Long>> = MutableLiveData()
    val addedItems: LiveData<Set<Long>> get() = _addedItems

    fun loadProductDetail(productId: Long) {
        runCatching {
            productRepository.productById(productId)
        }.onSuccess {
            _product.value = it
            _productUi.value = it.toProductUiModel()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun addProductToCart() {
        runCatching {
            val product = requireNotNull(_product.value)
            val cartItem = ShoppingCartItem(product)
            val userId = shoppingCartRepository.userId()
            val shoppingCart = shoppingCartRepository.shoppingCart(userId)
            shoppingCartRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
        }.onSuccess {
            val productId = requireNotNull(_product.value?.id)
            _addedItems.value = setOf(productId)
            _isAddSuccess.value = true
        }.onFailure {
            _isAddSuccess.value = false
        }
    }
}
