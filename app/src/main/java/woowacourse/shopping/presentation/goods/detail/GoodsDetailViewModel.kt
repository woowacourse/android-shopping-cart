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

    private val _count: MutableLiveData<Int> = MutableLiveData(MIN_PURCHASE_QUANTITY)
    val count: LiveData<Int>
        get() = _count

    private val _isItemAddedToCart: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val isItemAddedToCart: SingleLiveData<Unit>
        get() = _isItemAddedToCart

    fun setGoods(goods: GoodsUiModel) {
        _goods.value = goods
    }

    fun addToShoppingCart() {
        _goods.value?.let { shoppingRepository.addItemsWithCount(it.toDomain(), it.quantity + _count.value!!) }
        _isItemAddedToCart.setValue(Unit)
    }

    fun increaseCount() {
        _count.value = _count.value?.plus(QUANTITY_STEP)
    }

    fun decreaseCount() {
        _count.value = _count.value?.minus(QUANTITY_STEP)
    }

    companion object {
        private const val MIN_PURCHASE_QUANTITY: Int = 1
        private const val QUANTITY_STEP: Int = 1

        fun provideFactory(shoppingRepository: ShoppingRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    GoodsDetailViewModel(shoppingRepository)
                }
            }
    }
}
