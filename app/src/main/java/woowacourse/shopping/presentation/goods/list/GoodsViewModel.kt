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

    private val _latestGoods: MutableLiveData<List<GoodsUiModel>> = MutableLiveData()
    val latestGoods: LiveData<List<GoodsUiModel>>
        get() = _latestGoods

    private val _shouldShowLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowLoadMore: LiveData<Boolean>
        get() = _shouldShowLoadMore

    private val _shouldNavigateToShoppingCart: MutableSingleLiveData<Unit> = MutableSingleLiveData()
    val shouldNavigateToShoppingCart: SingleLiveData<Unit>
        get() = _shouldNavigateToShoppingCart

    private val _onQuantityChanged: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val onQuantityChanged: SingleLiveData<Int>
        get() = _onQuantityChanged

    private var page: Int = DEFAULT_PAGE

    fun initGoods() {
        val currentGoods = goodsRepository.getPagedGoods(page, ITEM_COUNT).map { it.toUiModel() }
        val selectedItems = shoppingRepository.getAllGoods()

        val updatedGoods = getUpdatedGoods(currentGoods, selectedItems)

        _goods.value = updatedGoods
        _shoppingGoodsCount.value = selectedItems.sumOf { it.goodsQuantity }
    }

    private fun getUpdatedGoods(
        currentGoods: List<GoodsUiModel>,
        selectedItems: Set<ShoppingGoods>,
    ): List<GoodsUiModel> {
        val updatedList =
            currentGoods.map { goods ->
                val selected = selectedItems.firstOrNull { it.goodsId == goods.id }
                goods.copy(quantity = selected?.goodsQuantity ?: MINIMUM_QUANTITY)
            }

        return updatedList
    }

    fun setLatestGoods() {
        _latestGoods.value =
            latestGoodsRepository.getAll().mapNotNull {
                goodsRepository.getById(it.goodsId)?.toUiModel()
            }
    }

    fun addToShoppingCart(position: Int) {
        val updatedItem =
            updateGoodsQuantity(position) {
                it.copy(quantity = it.quantity + QUANTITY_CHANGE_AMOUNT)
            }
        shoppingRepository.insertGoods(updatedItem.id, QUANTITY_CHANGE_AMOUNT)
        _shoppingGoodsCount.value = _shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT)
    }

    fun increaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoodsQuantity(position) {
                it.copy(quantity = it.quantity + QUANTITY_CHANGE_AMOUNT)
            }
        shoppingRepository.increaseGoodsQuantity(updatedItem.id)
        _shoppingGoodsCount.value = _shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT)
    }

    fun decreaseGoodsCount(position: Int) {
        val updatedItem =
            updateGoodsQuantity(position) {
                it.copy(quantity = it.quantity - QUANTITY_CHANGE_AMOUNT)
            }
        shoppingRepository.decreaseGoodsQuantity(updatedItem.id)
        _shoppingGoodsCount.value = _shoppingGoodsCount.value?.minus(QUANTITY_CHANGE_AMOUNT)

        if (_shoppingGoodsCount.value == 0) shoppingRepository.removeGoods(updatedItem.id)
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
        _onQuantityChanged.setValue(position)
        return updatedItem
    }

    fun loadMoreGoods() {
        _goods.value =
            _goods.value?.plus(
                goodsRepository.getPagedGoods(++page, ITEM_COUNT).map { it.toUiModel() },
            )
        _shouldShowLoadMore.value = false
    }

    fun updateShouldShowLoadMore() {
        _shouldShowLoadMore.value = canLoadMore()
    }

    private fun canLoadMore(): Boolean {
        return goodsRepository.getPagedGoods(page + 1, ITEM_COUNT).isNotEmpty()
    }

    fun updateShouldNavigateToShoppingCart() {
        _shouldNavigateToShoppingCart.setValue(Unit)
    }

    fun moveToDetail(
        goodsId: Int,
        move: (goodsId: Int, lastId: Int?) -> Unit,
    ) {
        val lastGoodsId = _latestGoods.value?.firstOrNull()?.id
        updateLatestGoods(goodsId)
        move(goodsId, lastGoodsId)
    }

    private fun updateLatestGoods(goodsId: Int) {
        latestGoodsRepository.insertLatestGoods(goodsId)
        _latestGoods.value =
            latestGoodsRepository.getAll().mapNotNull {
                goodsRepository.getById(it.goodsId)?.toUiModel()
            }
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
