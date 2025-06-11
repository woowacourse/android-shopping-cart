package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.RepositoryProvider

class GoodsDetailViewModelFactory(private val id: Long) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoodsDetailViewModel::class.java)) {
            return GoodsDetailViewModel(
                goodsId = id,
                goodsRepository = RepositoryProvider.goodsRepository,
                shoppingCartRepository = RepositoryProvider.shoppingCartRepository,
                recentGoodsRepository = RepositoryProvider.recentGoodsRepository,
            ) as T
        }
        throw IllegalArgumentException(VIEWMODEL_CLASS_ERROR)
    }

    companion object {
        private const val VIEWMODEL_CLASS_ERROR = "[ERROR] Unknown ViewModel Class"
    }
}