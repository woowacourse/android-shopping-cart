package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingCartApplication
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _productUiModelLiveData: MutableLiveData<ProductUiModel> = MutableLiveData()
    val productUiModelLiveData: LiveData<ProductUiModel> get() = _productUiModelLiveData
    val quantityLiveData: MutableLiveData<Int> = MutableLiveData(1)

    fun addProduct(productUiModel: ProductUiModel) {
        shoppingCartRepository.save(
            ShoppingCartItemUiModel(
                productUiModel = productUiModel,
                quantity = quantityLiveData.value!!,
            ),
        )
    }

    fun setProduct(productUiModel: ProductUiModel) {
        _productUiModelLiveData.value = productUiModel
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as ShoppingCartApplication)
                    ProductDetailViewModel(application.shoppingCartRepository)
                }
            }
    }
}
