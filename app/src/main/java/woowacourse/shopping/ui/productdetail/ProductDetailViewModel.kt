package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CartProduct.Companion.EMPTY_CART_PRODUCT
import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    private val _cartProduct: MutableLiveData<CartProduct> = MutableLiveData(EMPTY_CART_PRODUCT)
    val cartProduct: LiveData<CartProduct> get() = _cartProduct

    private val _lastHistoryProduct: MutableLiveData<HistoryProduct?> = MutableLiveData(null)
    val lastHistoryProduct: LiveData<HistoryProduct?> get() = _lastHistoryProduct

    private val _onCartProductAddSuccess: MutableSingleLiveData<Boolean?> = MutableSingleLiveData(null)
    val onCartProductAddSuccess: SingleLiveData<Boolean?> get() = _onCartProductAddSuccess

    fun loadProductDetail(id: Int) {
        productRepository.fetchProduct(id) { cartProduct ->
            _cartProduct.postValue(cartProduct)
        }
    }

    fun loadLastHistoryProduct() {
        historyRepository.fetchRecentSearchHistory { historyProduct ->
            _lastHistoryProduct.postValue(historyProduct)
        }
    }

    fun addHistoryProduct(id: Int) {
        historyRepository.saveSearchHistory(id)
    }

    fun decreaseCartProductQuantity() {
        _cartProduct.value = cartProduct.value?.decreaseQuantity()
    }

    fun increaseCartProductQuantity() {
        _cartProduct.value = cartProduct.value?.increaseQuantity()
    }

    fun updateCartProduct() {
        runCatching {
            cartRepository.saveCartProduct(cartProduct.value ?: return)
        }.onSuccess {
            _onCartProductAddSuccess.postValue(true)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY]) as ShoppingApp

                    return ProductDetailViewModel(
                        application.productRepository,
                        application.cartRepository,
                        application.historyRepository,
                    ) as T
                }
            }
    }
}
