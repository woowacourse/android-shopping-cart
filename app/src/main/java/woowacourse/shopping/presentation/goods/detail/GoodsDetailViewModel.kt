package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.data.ShoppingDataBase
import woowacourse.shopping.data.ShoppingRepository
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<Goods> = MutableLiveData()
    val goods: LiveData<Goods>
        get() = _goods

    private val _isItemAddedToCart: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val isItemAddedToCart: SingleLiveData<Unit>
        get() = _isItemAddedToCart

    fun setGoods(goods: Goods) {
        _goods.value = goods
    }

    fun addToShoppingCart() {
        _goods.value?.let { ShoppingDataBase.addItem(it) }
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
