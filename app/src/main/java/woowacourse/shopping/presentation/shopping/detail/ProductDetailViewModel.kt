package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.cart.CartProductUi
import woowacourse.shopping.presentation.cart.toUiModel
import woowacourse.shopping.presentation.common.MutableSingleLiveData
import woowacourse.shopping.presentation.common.SingleLiveData
import woowacourse.shopping.presentation.shopping.toCartUiModel

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {
    private val _cartProduct = MutableLiveData<CartProductUi>()
    val cartProduct: LiveData<CartProductUi> get() = _cartProduct

    private val _addCartEvent = MutableSingleLiveData<Unit>()
    val addCartEvent: SingleLiveData<Unit> get() = _addCartEvent

    private val _recentProductEvent = MutableSingleLiveData<Long>()
    val recentProductEvent: SingleLiveData<Long> get() = _recentProductEvent

    private val _recentProduct: MutableLiveData<Product> = MutableLiveData()
    val recentProduct: LiveData<Product> get() = _recentProduct

    val isRecentProductVisible: LiveData<Boolean>
        get() = _cartProduct.map {
            it.product.id != _recentProduct.value?.id
        }

    init {
        val recentProduct = shoppingRepository.recentProducts(1).getOrDefault(emptyList())
        if (recentProduct.isNotEmpty()) {
            _recentProduct.value = recentProduct.first()
        }
    }

    fun loadCartProduct(id: Long) {
        cartRepository.filterCarProducts(listOf(id)).onSuccess {
            if (it.isEmpty()) return loadProduct(id)
            _cartProduct.value = it.first().toUiModel()
        }.onFailure {
            // TODO Error handling
        }
    }

    private fun loadProduct(id: Long) {
        shoppingRepository.productById(id).onSuccess {
            _cartProduct.value = it.toCartUiModel()
        }.onFailure {
            // TODO Error handling
        }
    }

    fun increaseProductCount() {
        val cartProduct = _cartProduct.value ?: return
        _cartProduct.value = cartProduct.copy(count = cartProduct.count + 1)
    }

    fun decreaseProductCount() {
        val cartProduct = _cartProduct.value ?: return
        if (cartProduct.count <= 1) return // TODO : Handle error
        _cartProduct.value = cartProduct.copy(count = cartProduct.count - 1)
    }

    fun addCartProduct() {
        val cartProduct = _cartProduct.value ?: return
        // TODO CHANGE COUNT
        cartRepository.updateCartProduct(cartProduct.product.id, 1).onSuccess {
            _addCartEvent.setValue(Unit)
        }.onFailure {
            // TODO Error handling
        }
    }

    fun loadRecentProduct() {
        val recentId = _recentProduct.value?.id ?: return
        _recentProductEvent.setValue(recentId)
    }

    companion object {
        fun factory(
            cartRepository: CartRepository,
            shoppingRepository: ShoppingRepository
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    cartRepository,
                    shoppingRepository
                )
            }
        }
    }
}
