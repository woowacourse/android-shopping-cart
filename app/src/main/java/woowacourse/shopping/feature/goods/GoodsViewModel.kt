package woowacourse.shopping.feature.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods
import kotlin.math.min

class GoodsViewModel : ViewModel() {
    private val _goods = MutableLiveData<List<Goods>>()
    val goods: LiveData<List<Goods>> get() = _goods
    private var page: Int = 1
    private val _isFullLoaded = MutableLiveData(PAGE_SIZE >= dummyGoods.size)
    val isFullLoaded: LiveData<Boolean> get() = _isFullLoaded

    init {
        loadGoods()
    }

    private fun loadGoods() {
        val currentList = _goods.value ?: emptyList()
        val newList = getProducts(page)
        _goods.value = currentList + newList
        _isFullLoaded.value = page * PAGE_SIZE >= dummyGoods.size
    }

    fun addPage() {
        page++
        loadGoods()
    }

    private fun getProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<Goods> {
        val fromIndex = (page - 1) * pageSize
        val toIndex = min(page * pageSize, dummyGoods.size)
        return dummyGoods.subList(fromIndex, toIndex)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
