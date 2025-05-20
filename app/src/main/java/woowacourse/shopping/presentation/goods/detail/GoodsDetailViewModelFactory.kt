package woowacourse.shopping.presentation.goods.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shoppingcart.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.model.GoodsUiModel

class GoodsDetailViewModelFactory(
    private val goods: GoodsUiModel,
    private val repository: ShoppingCartRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoodsDetailViewModel::class.java)) {
            return GoodsDetailViewModel(goods, repository) as T
        }
        throw IllegalArgumentException(VIEWMODEL_ERROR)
    }

    companion object {
        private const val VIEWMODEL_ERROR = "[ERROR] Unknown ViewModel Class"
    }
}