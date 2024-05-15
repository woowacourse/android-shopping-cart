package woowacourse.shopping.presentation.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.product.ProductUi
import woowacourse.shopping.presentation.shopping.product.toUiModel


class ShoppingCartViewModel(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductUi>>(emptyList())
    val products: LiveData<List<ProductUi>> get() = _products

    init {
        _products.value = shoppingRepository.cartProducts().map { it.toUiModel() }
    }

    fun deleteProduct(position: Int) {
        val deletedItem = _products.value?.get(position)
        if (deletedItem != null) {
            _products.value = _products.value?.minus(deletedItem)
            shoppingRepository.deleteCartProduct(deletedItem.id)
        }
    }

    companion object {
        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ShoppingCartViewModel(repository) }
        }
    }
}

