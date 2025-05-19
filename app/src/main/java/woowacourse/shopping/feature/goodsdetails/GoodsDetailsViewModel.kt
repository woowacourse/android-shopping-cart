package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.Event
import woowacourse.shopping.util.toDomain

class GoodsDetailsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _toastMessage = MutableLiveData<Event<Int>>()
    val toastMessage: LiveData<Event<Int>> get() = _toastMessage

    fun insertToCart(goods: GoodsUiModel) {
        repository.insert(goods.toDomain())
        _toastMessage.value = Event(R.string.goods_detail_cart_insert_complete_toast_message)
    }
}
