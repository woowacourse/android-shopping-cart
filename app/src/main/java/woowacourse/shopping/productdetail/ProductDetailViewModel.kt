package woowacourse.shopping.productdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingCartRepository
import woowacourse.shopping.ProductRepository
import woowacourse.shopping.ViewModelQuantityActions
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel(), ViewModelQuantityActions {
    private lateinit var product: Product
    private var quantity: Quantity = Quantity()

    private val _productUi: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUi: LiveData<ProductUiModel> get() = _productUi

    private val _quantityUi: MutableLiveData<Int> = MutableLiveData()
    val quantityUi: LiveData<Int> get() = _quantityUi

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
            _quantityUi.value = quantity.value
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    override fun plusQuantity(productId: Long) {
        runCatching {
            quantity = quantity.increase()
            Log.d(this::class.java.simpleName, "${quantity.value}")

        }.onSuccess {
            _quantityUi.value = quantity.value
            updateProductPrice()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    override fun minusQuantity(productId: Long) {
        runCatching {
            quantity = quantity.decrease()
            Log.d(this::class.java.simpleName, "${quantity.value}")

        }.onSuccess {
            _quantityUi.value = quantity.value
            updateProductPrice()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun updateProductPrice() {
        val updatedPrice = product.price.times(quantity.value)
        _productUi.value =
            productUi.value?.copy(price = updatedPrice.value)
                ?: product.toProductUiModel()
    }

    fun addProductToCart() {
        runCatching {
            val cartItem = ShoppingCartItem(product, quantity.value)
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
