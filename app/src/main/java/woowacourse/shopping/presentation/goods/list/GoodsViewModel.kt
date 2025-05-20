package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.goods.repository.GoodsRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toUiModel

class GoodsViewModel(private val repository: GoodsRepository) : ViewModel() {
    private val _goodsUiModels: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val goodsUiModels: LiveData<List<GoodsUiModel>>
        get() = _goodsUiModels

    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoadMore: LiveData<Boolean>
        get() = _showLoadMore

    private var page: Int = DEFAULT_PAGE

    init {
        _goodsUiModels.value = repository.getPagedGoods(page++, ITEM_COUNT).map { it.toUiModel() }
    }

    fun addGoods() {
        _showLoadMore.value = false
        loadNextPage()
    }

    fun determineLoadMoreVisibility(canScroll: Boolean) {
        val moreDataAvailable = repository.getPagedGoods(page, ITEM_COUNT).isNotEmpty()
        _showLoadMore.value = !canScroll && moreDataAvailable
    }

    private fun loadNextPage() {
        val newGoods = repository.getPagedGoods(page++, ITEM_COUNT).map { it.toUiModel() }
        _goodsUiModels.value = _goodsUiModels.value.orEmpty() + newGoods
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val ITEM_COUNT: Int = 20
    }
}
