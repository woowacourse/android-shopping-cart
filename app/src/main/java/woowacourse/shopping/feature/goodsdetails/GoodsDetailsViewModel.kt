package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.toDomain

class GoodsDetailsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun insertToCart(goods: GoodsUiModel) {
        runCatching {
            repository.insert(goods.toDomain())
        }.onSuccess {
            _isSuccess.value = true
        }.onFailure {
            _isSuccess.value = false
        }
    }
}
