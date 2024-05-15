package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.product.ProductUi
import woowacourse.shopping.presentation.shopping.product.toUiModel

class ProductDetailViewModel(private val shoppingRepository: ShoppingRepository) : ViewModel() {
    private val _product = MutableLiveData<ProductUi>()
    val product: LiveData<ProductUi> get() = _product
    fun loadProduct(id: Long) {
        _product.value = shoppingRepository.productById(id).toUiModel()
    }

    companion object {
        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductDetailViewModel(repository) }
        }
    }
}
