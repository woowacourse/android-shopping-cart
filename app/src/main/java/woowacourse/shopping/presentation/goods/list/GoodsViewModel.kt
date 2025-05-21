package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val goods: LiveData<List<GoodsUiModel>>
        get() = _goods

    private val _shouldShowLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowLoadMore: LiveData<Boolean>
        get() = _shouldShowLoadMore

    private val _onQuantityChanged: MutableSingleLiveData<List<Int>> = MutableSingleLiveData()
    val onQuantityChanged: SingleLiveData<List<Int>>
        get() = _onQuantityChanged

    private var page: Int = DEFAULT_PAGE

    init {
        _goods.value = goodsRepository.getPagedGoods(page++, ITEM_COUNT).map { it.toUiModel() }
    }

    fun restoreGoods() {
        val currentGoods = goods.value.orEmpty()
        val selectedItems = shoppingRepository.getAllGoods()

        val changedPositions = mutableListOf<Int>()

        val updatedGoods =
            currentGoods.mapIndexed { index, goods ->
                val selected = selectedItems.firstOrNull { it.goodsId == goods.id }

                val updated =
                    when {
                        selected != null -> goods.copy(isSelected = true, quantity = selected.goodsQuantity)

                        goods.isSelected || goods.quantity != 0 -> goods.copy(isSelected = false, quantity = 0)

                        else -> goods
                    }

                if (updated != goods) {
                    changedPositions.add(index)
                }

                updated
            }

        _goods.value = updatedGoods
        _onQuantityChanged.setValue(changedPositions)
    }

    fun increaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoods(position) {
                it.copy(isSelected = true, quantity = it.quantity + 1)
            }
        shoppingRepository.increaseItemCount(updatedItem.id)
    }

    fun decreaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoods(position) {
                val isSelected = it.quantity - 1 != 0
                it.copy(isSelected = isSelected, quantity = it.quantity - 1)
            }
        shoppingRepository.decreaseItemCount(updatedItem.id)
    }

    private fun updateGoods(
        position: Int,
        transform: (GoodsUiModel) -> GoodsUiModel,
    ): GoodsUiModel {
        val currentGoods = goods.value.orEmpty()

        val updatedItem = transform(currentGoods[position])
        val updatedList =
            currentGoods.toMutableList().apply {
                this[position] = updatedItem
            }

        _goods.value = updatedList
        _onQuantityChanged.setValue(listOf(position))
        return updatedItem
    }

    fun loadMoreGoods() {
        _goods.value =
            _goods.value?.plus(
                goodsRepository.getPagedGoods(page++, ITEM_COUNT).map { it.toUiModel() },
            )
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

        fun provideFactory(
            goodsRepository: GoodsRepository,
            shoppingRepository: ShoppingRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    GoodsViewModel(goodsRepository, shoppingRepository)
                }
            }
    }
}
