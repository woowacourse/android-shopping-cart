package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.GoodsDataBase
import woowacourse.shopping.domain.model.Goods

class GoodsViewModel : ViewModel() {
    private val _goods: MutableLiveData<List<Goods>> = MutableLiveData()
    val goods: LiveData<List<Goods>>
        get() = _goods

    private var page: Int = DEFAULT_PAGE

    init {
        _goods.value = GoodsDataBase.getPagedGoods(page++, ITEM_COUNT)
    }

    fun addGoods() {
        _goods.value = _goods.value?.plus(GoodsDataBase.getPagedGoods(page++, ITEM_COUNT))
    }

    fun canLoadMore(): Boolean {
        return GoodsDataBase.getPagedGoods(page, ITEM_COUNT).isNotEmpty()
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val ITEM_COUNT: Int = 20
    }
}
