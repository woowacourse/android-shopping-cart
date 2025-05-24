package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsDetailViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
    private val latestGoodsRepository: LatestGoodsRepository,
) : ViewModel() {
    var lastGoods: GoodsUiModel? = null
        private set

    private val _goods: MutableLiveData<GoodsUiModel> = MutableLiveData()
    val goods: LiveData<GoodsUiModel>
        get() = _goods

    private val _count: MutableLiveData<Int> = MutableLiveData(MIN_PURCHASE_QUANTITY)
    val count: LiveData<Int>
        get() = _count

    private val _onItemAddedToCart: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val onItemAddedToCart: SingleLiveData<Int>
        get() = _onItemAddedToCart

    fun setGoods(id: Int) {
        _goods.value = goodsRepository.getById(id)?.toUiModel()
    }

    fun setLastGoods(id: Int?) {
        lastGoods =
            if (id == null || id == goods.value?.id) {
                null
            } else {
                goodsRepository.getById(id)?.toUiModel()
            }
    }

    fun addToShoppingCart() {
        val count = _count.value ?: MIN_PURCHASE_QUANTITY
        _goods.value?.let { goods -> shoppingRepository.increaseGoodsQuantity(goods.id, count) }
        _onItemAddedToCart.setValue(count)
    }

    fun increaseCount() {
        _count.value = _count.value?.plus(QUANTITY_STEP)
    }

    fun tryDecreaseCount() {
        if ((_count.value ?: MIN_PURCHASE_QUANTITY) > MIN_PURCHASE_QUANTITY) {
            _count.value = _count.value?.minus(QUANTITY_STEP)
        }
    }

    fun updateLatestGoods(goodsId: Int) {
        latestGoodsRepository.insertLatestGoods(goodsId)
    }

    companion object {
        private const val MIN_PURCHASE_QUANTITY: Int = 1
        private const val QUANTITY_STEP: Int = 1

        fun provideFactory(
            goodsRepository: GoodsRepository,
            shoppingRepository: ShoppingRepository,
            latestGoodsRepository: LatestGoodsRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    GoodsDetailViewModel(goodsRepository, shoppingRepository, latestGoodsRepository)
                }
            }
    }
}
