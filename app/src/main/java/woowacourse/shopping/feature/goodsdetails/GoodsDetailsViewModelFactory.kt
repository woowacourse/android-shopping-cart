package woowacourse.shopping.feature.goodsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.carts.repository.CartRepository
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.feature.GoodsUiModel

class GoodsDetailsViewModelFactory(
    private val goodsUiModel: GoodsUiModel,
    private val cartRepository: CartRepository,
    private val goodsRepository: GoodsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoodsDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoodsDetailsViewModel(goodsUiModel, cartRepository, goodsRepository) as T
        }
        throw IllegalArgumentException("알 수 없는 뷰 모델 클래스 입니다")
    }
}
