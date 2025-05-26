package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsDetailViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
    private val latestGoodsRepository: LatestGoodsRepository,
) : ViewModel() {
    var lastGoods: Goods? = null
        private set

    private val _goods: MutableLiveData<Goods> = MutableLiveData()
    val goods: LiveData<Goods>
        get() = _goods

    private val _onItemAddedToCart: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val onItemAddedToCart: SingleLiveData<Int>
        get() = _onItemAddedToCart

    fun setGoods(id: Int) {
        _goods.value = goodsRepository.getById(id)?.updateQuantity(MIN_PURCHASE_QUANTITY)
    }

    fun setLastGoods(id: Int?) {
        lastGoods =
            if (id == null || id == goods.value?.id) {
                null
            } else {
                goodsRepository.getById(id)
            }
    }

    fun addToShoppingCart() {
        _goods.value?.let { goods ->
            shoppingRepository.increaseGoodsQuantity(goods.id, goods.quantity) {
                _onItemAddedToCart.postValue(_goods.value?.quantity ?: MIN_PURCHASE_QUANTITY)
            }
        }
    }

    fun increaseCount() {
        _goods.value = goods.value?.increaseQuantity()
    }

    fun tryDecreaseCount() {
        if ((_goods.value?.quantity ?: MIN_PURCHASE_QUANTITY) > MIN_PURCHASE_QUANTITY) {
            _goods.value = goods.value?.decreaseQuantity()
        }
    }

    fun updateLatestGoods(goodsId: Int) {
        latestGoodsRepository.insertLatestGoods(goodsId) {}
    }

    companion object {
        private const val MIN_PURCHASE_QUANTITY: Int = 1

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
