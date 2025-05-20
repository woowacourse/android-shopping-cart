package woowacourse.shopping.presentation.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingcart.ShoppingDataBase
import woowacourse.shopping.data.shoppingcart.ShoppingDataBase.getPagedGoods
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toDomainModel
import woowacourse.shopping.presentation.model.toUiModel

class ShoppingCartViewModel : ViewModel() {
    private val _goodsUiModels: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val goodsUiModels: LiveData<List<GoodsUiModel>>
        get() = _goodsUiModels

    private val _page: MutableLiveData<Int> = MutableLiveData(DEFAULT_VALUE)
    val page: LiveData<Int>
        get() = _page

    private val _hasNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val hasNextPage: LiveData<Boolean>
        get() = _hasNextPage

    private val _hasPreviousPage: MutableLiveData<Boolean> = MutableLiveData()
    val hasPreviousPage: LiveData<Boolean>
        get() = _hasPreviousPage

    init {
        updateState()
    }

    fun deleteGoods(goodsUiModel: GoodsUiModel) {
        ShoppingDataBase.removeItem(goodsUiModel.toDomainModel())
        updateState()
    }

    fun increasePage() {
        _page.value = _page.value?.plus(PAGE_CHANGE_AMOUNT)
        updateState()
    }

    fun decreasePage() {
        _page.value = _page.value?.minus(PAGE_CHANGE_AMOUNT)
        updateState()
    }

    private fun updateState() {
        _goodsUiModels.value =
            getPagedGoods(_page.value ?: DEFAULT_VALUE, ITEM_COUNT).map { it.toUiModel() }
        updateNextPage()
        updatePreviousPage()
    }

    private fun updatePreviousPage() {
        _hasPreviousPage.value = _page.value != DEFAULT_VALUE
    }

    private fun updateNextPage() {
        _hasNextPage.value =
            getPagedGoods(
                _page.value?.plus(PAGE_CHANGE_AMOUNT) ?: DEFAULT_VALUE,
                ITEM_COUNT,
            ).isNotEmpty()
    }

    companion object {
        private const val ITEM_COUNT: Int = 5
        private const val DEFAULT_VALUE: Int = 1
        private const val PAGE_CHANGE_AMOUNT: Int = 1
    }
}
