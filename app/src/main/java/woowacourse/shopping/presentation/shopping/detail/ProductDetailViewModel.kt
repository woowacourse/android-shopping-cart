package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.common.MutableSingleLiveData
import woowacourse.shopping.presentation.common.SingleLiveData
import woowacourse.shopping.presentation.shopping.toUiModel

class ProductDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUi>()
    val product: LiveData<ProductUi> get() = _product

    private val _isAddedCart = MutableSingleLiveData<Boolean>()
    val isAddedCart: SingleLiveData<Boolean> get() = _isAddedCart

    fun loadProduct(id: Long) {
        shoppingRepository.productById(id).onSuccess {
            _product.value = it.toUiModel()
        }.onFailure {
            // TODO Error handling
        }
    }

    fun addCartProduct() {
        val product = _product.value ?: return
        // TODO CHANGE COUNT
        cartRepository.addCartProduct(product.id, 1).onSuccess {
            _isAddedCart.setValue(true)
        }.onFailure {
            // TODO Error handling
        }
    }

    companion object {
        fun factory(
            shoppingRepository: ShoppingRepository,
            cartRepository: CartRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    shoppingRepository,
                    cartRepository,
                )
            }
        }
    }
}
