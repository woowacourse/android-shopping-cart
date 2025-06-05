package woowacourse.shopping.feature.goods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.data.goods.repository.GoodsRepository

class GoodsViewModelFactory(
    private val cartRepository: CartRepository,
    private val goodsRepository: GoodsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoodsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoodsViewModel(cartRepository, goodsRepository) as T
        }
        throw IllegalArgumentException("알 수 없는 뷰 모델 클래스 입니다")
    }
}
