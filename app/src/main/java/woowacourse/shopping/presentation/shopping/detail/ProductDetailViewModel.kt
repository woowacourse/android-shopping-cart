package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.cart.toUiModel
import woowacourse.shopping.presentation.shopping.toCartUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel(), DetailProductListener {
    private val _uiState = MutableLiveData<ProductDetailUiState>(ProductDetailUiState.init())
    val uiState: LiveData<ProductDetailUiState> get() = _uiState

    private val _updateCartEvent = MutableSingleLiveData<Unit>()
    val updateCartEvent: SingleLiveData<Unit> get() = _updateCartEvent

    private val _addCartEvent = MutableSingleLiveData<Unit>()
    val addCartEvent: SingleLiveData<Unit> get() = _addCartEvent

    private val _recentProductEvent = MutableSingleLiveData<Long>()
    val recentProductEvent: SingleLiveData<Long> get() = _recentProductEvent

    private val _errorEvent: MutableSingleLiveData<ProductDetailErrorEvent> =
        MutableSingleLiveData()
    val errorEvent: SingleLiveData<ProductDetailErrorEvent> get() = _errorEvent

    init {
        loadRecentProduct()
    }

    fun loadCartProduct(id: Long) {
        shoppingRepository.saveRecentProduct(id)
        cartRepository.filterCartProducts(listOf(id)).onSuccess {
            if (it.isEmpty()) {
                return loadProduct(id)
            }
            _uiState.value = uiState.value?.copy(cartProduct = it.first().toUiModel())
        }.onFailure {
            _errorEvent.setValue(ProductDetailErrorEvent.LoadCartProduct)
        }
    }

    fun refreshCartProduct() {
        val id = _uiState.value?.cartProduct?.product?.id ?: return
        loadCartProduct(id)
    }

    override fun increaseProductCount(id: Long) {
        val newUiState = uiState.value?.increaseProductCount(INCREMENT_AMOUNT) ?: return
        _uiState.value = newUiState
    }

    override fun decreaseProductCount(id: Long) {
        if (uiState.value?.canDecreaseProductCount() != true) {
            return _errorEvent.setValue(ProductDetailErrorEvent.DecreaseCartCount)
        }
        val newUiState = uiState.value?.decreaseProductCount(INCREMENT_AMOUNT) ?: return
        _uiState.value = newUiState
    }

    override fun addCartProduct() {
        val cartProduct = uiState.value?.cartProduct ?: return
        cartRepository.updateCartProduct(cartProduct.product.id, cartProduct.count).onSuccess {
            _addCartEvent.setValue(Unit)
            _updateCartEvent.setValue(Unit)
        }.onFailure {
            _errorEvent.setValue(ProductDetailErrorEvent.AddCartProduct)
        }
    }

    fun navigateToRecentProduct() {
        val recentId = _uiState.value?.recentProduct?.id ?: return
        shoppingRepository.saveRecentProduct(recentId).onSuccess {
            _recentProductEvent.setValue(recentId)
        }.onFailure {
            _errorEvent.setValue(ProductDetailErrorEvent.SaveRecentProduct)
        }
    }

    private fun loadProduct(id: Long) {
        shoppingRepository.productById(id).onSuccess {
            _uiState.value = uiState.value?.copy(cartProduct = it.toCartUiModel())
        }.onFailure {
            _errorEvent.setValue(ProductDetailErrorEvent.LoadCartProduct)
        }
    }

    private fun loadRecentProduct() {
        shoppingRepository.recentProducts(1).onSuccess {
            if (it.isEmpty()) return
            _uiState.value = uiState.value?.copy(recentProduct = it.first())
        }.onFailure {
            _errorEvent.setValue(ProductDetailErrorEvent.LoadCartProduct)
        }
    }

    companion object {
        private const val INCREMENT_AMOUNT = 1

        fun factory(
            cartRepository: CartRepository,
            shoppingRepository: ShoppingRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    cartRepository,
                    shoppingRepository,
                )
            }
        }
    }
}
