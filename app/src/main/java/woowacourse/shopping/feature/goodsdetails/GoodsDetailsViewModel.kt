package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.toDomain

class GoodsDetailsViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _goods = MutableLiveData<Goods>()
    val goods: LiveData<Goods> get() = _goods

    private val _alertEvent = MutableLiveData<Int>()
    val alertEvent: LiveData<Int> get() = _alertEvent

    fun addToCart() {
        goods.value?.let {
            cartRepository.insert(it) {
                _alertEvent.postValue(R.string.goods_detail_cart_insert_complete_toast_message)
            }
        }
    }

    fun setGoods(goodsUiModel: GoodsUiModel) {
        _goods.postValue(goodsUiModel.toDomain())
    }
}
