package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.toDomain

class GoodsDetailsViewModel(
    goodsUiModel: GoodsUiModel,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _goods = MutableLiveData<Goods>()
    val goods: LiveData<Goods> get() = _goods
    private val _alertEvent = MutableSingleLiveData<Int>()
    val alertEvent: SingleLiveData<Int> = _alertEvent

    init {
        _goods.value = goodsUiModel.toDomain()
    }

    fun addToCart() {
        goods.value?.let {
            cartRepository.insertOrAddQuantity(it, 1) {
                _alertEvent.setValue(R.string.goods_detail_cart_insert_complete_toast_message)
            }
        }
    }
}
