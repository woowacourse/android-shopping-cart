package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.toDomain

class GoodsDetailsViewModel(
    private val goodsUiModel: GoodsUiModel,
    private val cartRepository: CartRepository,
    private val goodsRepository: GoodsRepository,
) : ViewModel() {
    private val _cartItem: MutableLiveData<CartItem> =
        MutableLiveData(CartItem(goodsUiModel.toDomain(), 1))
    val cartItem: LiveData<CartItem> get() = _cartItem
    private val _alertEvent = MutableSingleLiveData<GoodsDetailsAlertMessage>()
    val alertEvent: SingleLiveData<GoodsDetailsAlertMessage> = _alertEvent
    private val _mostRecentlyViewedGoods: MutableLiveData<Goods?> = MutableLiveData(null)
    val mostRecentlyViewedGoods: LiveData<Goods?> get() = _mostRecentlyViewedGoods
    private val _clickMostRecentlyGoodsEvent = MutableSingleLiveData<Goods>()
    val clickMostRecentlyGoodsEvent: SingleLiveData<Goods> get() = _clickMostRecentlyGoodsEvent

    fun initMostRecentlyViewedGoods() {
        goodsRepository.fetchMostRecentGoods { goods ->
            goods?.let {
                if (goodsUiModel.id != goods.id) _mostRecentlyViewedGoods.postValue(it)
            }
            loggingRecentViewedGoods(goodsUiModel.toDomain())
        }
    }

    fun increaseSelectorQuantity() {
        _cartItem.value?.let { currentItem ->
            _cartItem.value = currentItem.copy(quantity = currentItem.quantity + 1)
        }
    }

    fun decreaseSelectorQuantity() {
        _cartItem.value?.let { currentItem ->
            if (currentItem.quantity > 1) {
                _cartItem.value = currentItem.copy(quantity = currentItem.quantity - 1)
            }
        }
    }

    fun addToCart() {
        cartItem.value?.let {
            cartRepository.addOrIncreaseQuantity(it.goods, it.quantity) {
                _alertEvent.setValue(
                    GoodsDetailsAlertMessage(
                        R.string.goods_detail_cart_insert_complete_toast_message,
                        it.quantity,
                    ),
                )
                _cartItem.value = _cartItem.value?.copy(quantity = 1)
            }
        }
    }

    fun onClickMostRecentlyGoodsSection() {
        _mostRecentlyViewedGoods.value?.let {
            _clickMostRecentlyGoodsEvent.setValue(it)
        }
    }

    fun loggingRecentViewedGoods(goods: Goods) {
        goodsRepository.loggingRecentGoods(goods) {}
    }
}
