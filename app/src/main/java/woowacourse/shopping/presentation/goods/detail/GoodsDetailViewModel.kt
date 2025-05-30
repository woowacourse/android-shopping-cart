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
    private val _lastGoods: MutableLiveData<Goods> = MutableLiveData()
    val lastGoods: LiveData<Goods>
        get() = _lastGoods

    private val _goods: MutableLiveData<Goods> = MutableLiveData()
    val goods: LiveData<Goods>
        get() = _goods

    private val _onItemAddedToCart: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val onItemAddedToCart: SingleLiveData<Int>
        get() = _onItemAddedToCart

    fun setGoodsAndLast(
        id: Int,
        lastId: Int?,
    ): Result<Unit> {
        return runCatching {
            goodsRepository.getById(
                id,
                onSuccess = {
                    _goods.postValue(it?.updateQuantity(MIN_PURCHASE_QUANTITY))
                    setLastGoods(id, lastId)
                },
            )
        }
    }

    private fun setLastGoods(
        id: Int,
        lastId: Int?,
    ) {
        if (lastId != null && lastId != id) {
            goodsRepository.getById(lastId) {
                _lastGoods.postValue(it)
            }
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
