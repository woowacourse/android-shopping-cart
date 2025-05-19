package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.GoodsDataBase
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toUiModel

class GoodsViewModel : ViewModel() {
    private val _goodsUiModels: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val goodsUiModels: LiveData<List<GoodsUiModel>>
        get() = _goodsUiModels

    private var page: Int = DEFAULT_PAGE

    init {
        _goodsUiModels.value =
            GoodsDataBase.getPagedGoods(page++, ITEM_COUNT).map { it.toUiModel() }
    }

    fun addGoods() {
        _goodsUiModels.value =
            _goodsUiModels.value?.plus(
                GoodsDataBase.getPagedGoods(page++, ITEM_COUNT).map { it.toUiModel() },
            )
    }

    fun canLoadMore(): Boolean {
        return GoodsDataBase.getPagedGoods(page, ITEM_COUNT).isNotEmpty()
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val ITEM_COUNT: Int = 20
    }
}
