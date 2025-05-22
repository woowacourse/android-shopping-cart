package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.model.ShoppingGoods
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.GoodsUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
    private val latestGoodsRepository: LatestGoodsRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val goods: LiveData<List<GoodsUiModel>>
        get() = _goods

    private val _shoppingGoodsCount: MutableLiveData<Int> = MutableLiveData(MINIMUM_QUANTITY)
    val shoppingGoodsCount: LiveData<Int>
        get() = _shoppingGoodsCount

    private val _shouldShowLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowLoadMore: LiveData<Boolean>
        get() = _shouldShowLoadMore

    private val _shouldNavigateToShoppingCart: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val shouldNavigateToShoppingCart: SingleLiveData<Unit>
        get() = _shouldNavigateToShoppingCart

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

        val (updatedGoods, changedPositions) = getUpdatedGoods(currentGoods, selectedItems)

        _goods.value = updatedGoods
        _onQuantityChanged.setValue(changedPositions)
        _shoppingGoodsCount.value = selectedItems.sumOf { it.goodsQuantity }
    }

    private fun getUpdatedGoods(
        currentGoods: List<GoodsUiModel>,
        selectedItems: Set<ShoppingGoods>,
    ): Pair<List<GoodsUiModel>, List<Int>> {
        val changedPositions = mutableListOf<Int>()

        val updatedList =
            currentGoods.mapIndexed { index, goods ->
                val updated = updateGoodsFromSelected(goods, selectedItems)
                if (updated != goods) {
                    changedPositions.add(index)
                }

                updated
            }

        return updatedList to changedPositions
    }

    private fun updateGoodsFromSelected(
        goods: GoodsUiModel,
        selectedItems: Set<ShoppingGoods>,
    ): GoodsUiModel {
        val selected = selectedItems.firstOrNull { it.goodsId == goods.id }

        return when {
            selected != null -> goods.copy(quantity = selected.goodsQuantity)
            goods.quantity != MINIMUM_QUANTITY -> goods.copy(quantity = MINIMUM_QUANTITY)
            else -> goods
        }
    }

    fun increaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoodsQuantity(position) {
                it.copy(quantity = it.quantity + QUANTITY_CHANGE_AMOUNT)
            }
        shoppingRepository.increaseItemQuantity(updatedItem.id)
        _shoppingGoodsCount.value = _shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT)
    }

    fun decreaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoodsQuantity(position) {
                it.copy(quantity = it.quantity - QUANTITY_CHANGE_AMOUNT)
            }
        shoppingRepository.decreaseItemQuantity(updatedItem.id)
        _shoppingGoodsCount.value = _shoppingGoodsCount.value?.minus(QUANTITY_CHANGE_AMOUNT)
    }

    private fun updateGoodsQuantity(
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

    fun updateShouldNavigateToShoppingCart() {
        _shouldNavigateToShoppingCart.setValue(Unit)
    }

    fun updateLatestGoods(goods: GoodsUiModel) {
        latestGoodsRepository.insertLatestGoods(goods.id)
    }

    private fun canLoadMore(): Boolean {
        return goodsRepository.getPagedGoods(page, ITEM_COUNT).isNotEmpty()
    }

    companion object {
        private const val QUANTITY_CHANGE_AMOUNT: Int = 1
        private const val DEFAULT_PAGE: Int = 0
        private const val MINIMUM_QUANTITY: Int = 0
        private const val ITEM_COUNT: Int = 20

        fun provideFactory(
            goodsRepository: GoodsRepository,
            shoppingRepository: ShoppingRepository,
            latestGoodsRepository: LatestGoodsRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    GoodsViewModel(goodsRepository, shoppingRepository, latestGoodsRepository)
                }
            }
    }
}
