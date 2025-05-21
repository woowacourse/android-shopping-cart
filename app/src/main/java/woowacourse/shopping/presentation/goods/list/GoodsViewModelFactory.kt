package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.GoodsRepository

class GoodsViewModelFactory(private val repository: GoodsRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoodsViewModel::class.java)) {
            return GoodsViewModel(repository) as T
        }
        throw IllegalArgumentException(VIEWMODEL_ERROR)
    }

    companion object {
        private const val VIEWMODEL_ERROR = "[ERROR] Unknown ViewModel Class"
    }
}
