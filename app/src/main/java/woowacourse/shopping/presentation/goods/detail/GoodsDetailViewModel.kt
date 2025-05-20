package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.data.ShoppingRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toDomain
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<GoodsUiModel> = MutableLiveData()
    val goods: LiveData<GoodsUiModel>
        get() = _goods

    private val _isItemAddedToCart: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val isItemAddedToCart: SingleLiveData<Unit>
        get() = _isItemAddedToCart

    fun setGoods(goods: GoodsUiModel) {
        _goods.value = goods
    }

    fun addToShoppingCart() {
        _goods.value?.let { shoppingRepository.addItem(it.toDomain()) }
        _isItemAddedToCart.setValue(Unit)
    }

    companion object {
        fun provideFactory(shoppingRepository: ShoppingRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    GoodsDetailViewModel(shoppingRepository)
                }
            }
    }
}
