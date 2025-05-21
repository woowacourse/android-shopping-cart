package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.feature.model.CartUiModel
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import woowacourse.shopping.util.toDomain

class GoodsDetailsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _isSuccess = MutableSingleLiveData<Unit>()
    val isSuccess: SingleLiveData<Unit> get() = _isSuccess
    private val _isFail = MutableSingleLiveData<Unit>()
    val isFail: SingleLiveData<Unit> get() = _isFail

    fun insertToCart(cart: CartUiModel) {
        try {
            repository.insert(cart.toDomain())
            _isSuccess.setValue(Unit)
        } catch (e: Exception) {
            _isFail.setValue(Unit)
        }
    }
}
