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
        _goods.value?.let { goods ->
            if (shoppingRepository.getGoodsById(goods.id) != null) {
                shoppingRepository.increaseGoodsQuantity(goods.id, goods.quantity)
            } else {
                shoppingRepository.insertGoods(goods.id, goods.quantity)
            }
        }
        _onItemAddedToCart.setValue(_goods.value?.quantity ?: MIN_PURCHASE_QUANTITY)
    }

    fun increaseCount() {
        _goods.value = goods.value?.copy(quantity = goods.value?.quantity?.plus(QUANTITY_STEP) ?: MIN_PURCHASE_QUANTITY)
    }

    fun tryDecreaseCount() {
        if ((_goods.value?.quantity ?: MIN_PURCHASE_QUANTITY) > MIN_PURCHASE_QUANTITY) {
            _goods.value = goods.value?.copy(quantity = goods.value?.quantity?.minus(QUANTITY_STEP) ?: MIN_PURCHASE_QUANTITY)
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
