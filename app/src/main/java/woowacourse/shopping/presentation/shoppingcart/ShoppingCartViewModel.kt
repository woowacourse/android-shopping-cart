package woowacourse.shopping.presentation.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.data.GoodsRepository
import woowacourse.shopping.data.ShoppingRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toDomain
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class ShoppingCartViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val goods: LiveData<List<GoodsUiModel>>
        get() = _goods

    private val _page: MutableLiveData<Int> = MutableLiveData(DEFAULT_PAGE_VALUE)
    val page: LiveData<Int>
        get() = _page

    private val _hasNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val hasNextPage: LiveData<Boolean>
        get() = _hasNextPage

    private val _hasPreviousPage: MutableLiveData<Boolean> = MutableLiveData()
    val hasPreviousPage: LiveData<Boolean>
        get() = _hasPreviousPage

    private val _isQuantityChanged: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val isQuantityChanged: SingleLiveData<Int>
        get() = _isQuantityChanged

    init {
        updateState()
    }

    fun deleteGoods(goods: GoodsUiModel) {
        shoppingRepository.removeItem(goods.id)
        updateState()
    }

    fun increaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoods(position) {
                it.copy(quantity = it.quantity + 1)
            }
        shoppingRepository.increaseItemCount(updatedItem.toDomain())
    }

    fun decreaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoods(position) {
                it.copy(quantity = it.quantity - 1)
            }

        if (updatedItem.quantity <= 0) {
            deleteGoods(updatedItem)
        } else {
            shoppingRepository.decreaseItemCount(updatedItem.id)
        }
    }

    private fun updateGoods(
        position: Int,
        transform: (GoodsUiModel) -> GoodsUiModel,
    ): GoodsUiModel {
        val currentList = goods.value.orEmpty()

        val updatedItem = transform(currentList[position])
        val updatedList =
            currentList.toMutableList().apply {
                this[position] = updatedItem
            }

        _goods.value = updatedList
        _isQuantityChanged.setValue(position)
        return updatedItem
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
        _goods.value =
            shoppingRepository.getPagedGoods(_page.value ?: DEFAULT_PAGE_VALUE, ITEM_COUNT)
                .map { goodsRepository.getById(it.goodsId).toUiModel().copy(quantity = it.goodsCount) }
        updateNextPage()
        updatePreviousPage()
    }

    private fun updatePreviousPage() {
        _hasPreviousPage.value = _page.value != DEFAULT_PAGE_VALUE
    }

    private fun updateNextPage() {
        _hasNextPage.value =
            shoppingRepository.getPagedGoods(
                _page.value?.plus(PAGE_CHANGE_AMOUNT) ?: DEFAULT_PAGE_VALUE,
                ITEM_COUNT,
            ).isNotEmpty()
    }

    companion object {
        private const val ITEM_COUNT: Int = 5
        private const val DEFAULT_PAGE_VALUE: Int = 1
        private const val PAGE_CHANGE_AMOUNT: Int = 1

        fun provideFactory(
            goodsRepository: GoodsRepository,
            shoppingRepository: ShoppingRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ShoppingCartViewModel(goodsRepository, shoppingRepository)
                }
            }
    }
}
