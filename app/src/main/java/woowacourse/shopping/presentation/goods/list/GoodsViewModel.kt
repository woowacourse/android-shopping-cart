package woowacourse.shopping.presentation.goods.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.ShoppingGoods
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

class GoodsViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
    private val latestGoodsRepository: LatestGoodsRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<List<Goods>> = MutableLiveData()
    val goods: LiveData<List<Goods>>
        get() = _goods

    private val _shoppingGoodsCount: MutableLiveData<Int> = MutableLiveData(MINIMUM_QUANTITY)
    val shoppingGoodsCount: LiveData<Int>
        get() = _shoppingGoodsCount

    private val _latestGoods: MutableLiveData<List<Goods>> = MutableLiveData()
    val latestGoods: LiveData<List<Goods>>
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
        _goods.value ?: goodsRepository.getPagedGoods(page, ITEM_COUNT) { currentGoods ->
            shoppingRepository.getAllGoods { selectedItems ->
                val updatedGoods = getUpdatedGoods(currentGoods, selectedItems)

                _goods.postValue(updatedGoods)
                _shoppingGoodsCount.postValue(selectedItems.sumOf { it.goodsQuantity })
            }
        }
    }

    private fun getUpdatedGoods(
        currentGoods: List<Goods>,
        selectedItems: Set<ShoppingGoods>,
    ): List<Goods> {
        val updatedList =
            currentGoods.map { goods ->
                val selected = selectedItems.firstOrNull { it.goodsId == goods.id }
                goods.updateQuantity(selected?.goodsQuantity ?: MINIMUM_QUANTITY)
            }

        return updatedList
    }

    fun setLatestGoods() {
        latestGoodsRepository.getAll { latestGoodsList ->
            val updatedList = mutableListOf<Goods>()

            latestGoodsList.forEach { item ->
                goodsRepository.getById(item.goodsId)?.let { updatedList.add(it) }
            }
            _latestGoods.postValue(updatedList)
        }
    }

    fun addToShoppingCart(goodsId: Int) {
        val updatedItem =
            updateGoodsQuantity(goodsId) {
                it.increaseQuantity()
            }
        shoppingRepository.insertGoods(updatedItem.id, QUANTITY_CHANGE_AMOUNT) {
            _shoppingGoodsCount.postValue(_shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT))
        }
    }

    fun increaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoodsQuantity(goodsId) {
                it.increaseQuantity()
            }
        shoppingRepository.increaseGoodsQuantity(updatedItem.id) {
            _shoppingGoodsCount.postValue(_shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT))
        }
    }

    fun decreaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoodsQuantity(goodsId) {
                it.decreaseQuantity()
            }
        shoppingRepository.decreaseGoodsQuantity(updatedItem.id) {
            _shoppingGoodsCount.postValue(_shoppingGoodsCount.value?.minus(QUANTITY_CHANGE_AMOUNT))
        }
    }

    private fun updateGoodsQuantity(
        goodsId: Int,
        transform: (Goods) -> Goods,
    ): Goods {
        var updatedItem: Goods? = null
        val updatedList =
            goods.value.orEmpty().mapIndexed { _, item ->
                if (item.id == goodsId) {
                    val transformed = transform(item)
                    updatedItem = transformed
                    transformed
                } else {
                    item
                }
            }

        _goods.value = updatedList
        _onQuantityChanged.setValue(goodsId)
        return updatedItem ?: throw IllegalStateException("id $goodsId 해당하는 Goods가 없습니다")
    }

    fun loadMoreGoods() {
        goodsRepository.getPagedGoods(page + 1, ITEM_COUNT) { moreGoods ->

            if (moreGoods.isNotEmpty()) {
                _goods.postValue(_goods.value?.plus(moreGoods))
                page.plus(1)
            } else {
                Log.e("GoodsViewModel", "상품 로드 실패")
            }
            _shouldShowLoadMore.postValue(false)
        }
    }

    fun updateShouldShowLoadMore() {
        goodsRepository.getPagedGoods(page + 1, ITEM_COUNT) {
            _shouldShowLoadMore.postValue(it.isNotEmpty())
        }
    }

    fun updateShouldNavigateToShoppingCart() {
        _shouldNavigateToShoppingCart.setValue(Unit)
    }

    fun moveToDetail(
        goodsId: Int,
        move: (goodsId: Int, lastId: Int?) -> Unit,
    ) {
        latestGoodsRepository.getLast { latestGoods ->
            updateLatestGoods(goodsId)
            move(goodsId, latestGoods?.goodsId)
        }
    }

    private fun updateLatestGoods(goodsId: Int) {
        latestGoodsRepository.insertLatestGoods(goodsId) {
            setLatestGoods()
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
