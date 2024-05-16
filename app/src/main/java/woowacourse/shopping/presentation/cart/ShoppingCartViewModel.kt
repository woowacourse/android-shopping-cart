package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.toUiModel


class ShoppingCartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductUi>>(emptyList())
    val products: LiveData<List<ProductUi>> get() = _products

    init {
        _products.value = cartRepository.cartProducts().map { it.toUiModel() }
    }

    fun deleteProduct(position: Int) {
        val deletedItem = _products.value?.get(position)
        if (deletedItem != null) {
            _products.value = _products.value?.minus(deletedItem)
            cartRepository.deleteCartProduct(deletedItem.id)
        }
    }

    companion object {
        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ShoppingCartViewModel(repository) }
        }
    }
}

