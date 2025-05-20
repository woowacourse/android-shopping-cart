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
    private val _showMoreButton = MutableLiveData(false)
    val showMoreButton: LiveData<Boolean> get() = _showMoreButton
    private val _isFullLoaded = MutableLiveData<Boolean>()
    val isFullLoaded: LiveData<Boolean> get() = _isFullLoaded
    private var page: Int = 0

    init {
        loadGoods()
    }

    fun loadGoods() {
        val currentList = _goods.value ?: emptyList()
        val newList = getProducts(page)
        _goods.value = currentList + newList
        _isFullLoaded.value = (page + 1) * PAGE_SIZE >= dummyGoods.size
    }

    fun addPage() {
        page++
        loadGoods()
    }

    fun updateMoreButtonVisibility(shouldShow: Boolean) {
        _showMoreButton.value = shouldShow
    }

    fun getProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<Goods> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, dummyGoods.size)
        return dummyGoods.subList(fromIndex, toIndex)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
