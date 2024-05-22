package woowacourse.shopping.productdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.domain.ShoppingCartItem

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _product: MutableLiveData<ProductUiModel> = MutableLiveData()
    val product: LiveData<ProductUiModel> get() = _product

    private val _isAddSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAddSuccess: LiveData<Boolean> get() = _isAddSuccess

    fun loadProductDetail(productId: Long) {
        runCatching {
            productRepository.productById(productId)
        }.onSuccess {
            _product.value = it.toProductUiModel(1)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun addProductToCart(productId: Long) {
        runCatching {
            val cartItem = ShoppingCartItem(productRepository.productById(productId))
            val shoppingCart = shoppingRepository.shoppingCart()
            shoppingRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
        }.onSuccess {
            _isAddSuccess.value = true
        }.onFailure {
            _isAddSuccess.value = false
        }
    }
}
