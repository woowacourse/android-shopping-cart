package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.Goods

class GoodsDetailsViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    fun insertToCart(
        goods: Goods,
        onCompleteInsert: () -> Unit,
    ) {
        repository.insert(goods) { onCompleteInsert }
    }
}
