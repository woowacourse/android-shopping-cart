package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.data.GoodsRepository
import woowacourse.shopping.domain.model.Goods

class GoodsViewModel(
    private val goodsRepository: GoodsRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<List<Goods>> = MutableLiveData()
    val goods: LiveData<List<Goods>>
        get() = _goods

    private val _shouldShowLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowLoadMore: LiveData<Boolean>
        get() = _shouldShowLoadMore

    private var page: Int = DEFAULT_PAGE

    init {
        _goods.value = goodsRepository.getPagedGoods(page++, ITEM_COUNT)
    }

    fun addGoods() {
        _goods.value = _goods.value?.plus(goodsRepository.getPagedGoods(page++, ITEM_COUNT))
        _shouldShowLoadMore.value = false
    }

    fun updateShouldShowLoadMore() {
        _shouldShowLoadMore.value = canLoadMore()
    }

    private fun canLoadMore(): Boolean {
        return goodsRepository.getPagedGoods(page, ITEM_COUNT).isNotEmpty()
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val ITEM_COUNT: Int = 20

        fun provideFactory(goodsRepository: GoodsRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    GoodsViewModel(goodsRepository)
                }
            }
    }
}
