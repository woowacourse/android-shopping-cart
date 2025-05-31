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

    private var page: Int = DEFAULT_PAGE

    fun initGoods() {
        goodsRepository.getPagedGoods(page, ITEM_COUNT, onSuccess = { currentGoods ->
            shoppingRepository.getAllGoods(onSuccess = { selectedItems ->
                val updatedGoods = getUpdatedGoods(currentGoods, selectedItems)

                _goods.postValue(updatedGoods)
                _shoppingGoodsCount.postValue(selectedItems.sumOf { it.goodsQuantity })
            }, onFailure = { errorMessage ->
                Log.e(TAG, "initGoods: $errorMessage")
            })
        }, onFailure = { errorMessage ->
            Log.e(TAG, "initGoods: $errorMessage")
        })
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
        latestGoodsRepository.getAll(onSuccess = { latestGoodsList ->
            goodsRepository.getGoodsListByIds(
                latestGoodsList.map { it.goodsId },
                onSuccess = { latestGoods ->
                    _latestGoods.postValue(latestGoods)
                },
                onFailure = { errorMessage ->
                    Log.e(TAG, "setLatestGoods: $errorMessage")
                },
            )
        }, onFailure = { errorMessage ->
            Log.e(TAG, "setLatestGoods: $errorMessage")
        })
    }

    fun addToShoppingCart(goodsId: Int) {
        val updatedItem =
            updateGoodsQuantity(goodsId) {
                it.increaseQuantity()
            }
        shoppingRepository.insertGoods(updatedItem.id, QUANTITY_CHANGE_AMOUNT, onSuccess = {
            _shoppingGoodsCount.postValue(_shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT))
        }, onFailure = { errorMessage ->
            Log.e(TAG, "addToShoppingCart: $errorMessage")
        })
    }

    fun increaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoodsQuantity(goodsId) {
                it.increaseQuantity()
            }
        shoppingRepository.increaseGoodsQuantity(updatedItem.id, onSuccess = {
            _shoppingGoodsCount.postValue(_shoppingGoodsCount.value?.plus(QUANTITY_CHANGE_AMOUNT))
        }, onFailure = { errorMessage ->
            Log.e(TAG, "increaseGoodsCount: $errorMessage")
        })
    }

    fun decreaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoodsQuantity(goodsId) {
                it.decreaseQuantity()
            }
        shoppingRepository.decreaseGoodsQuantity(updatedItem.id, onSuccess = {
            _shoppingGoodsCount.postValue(_shoppingGoodsCount.value?.minus(QUANTITY_CHANGE_AMOUNT))
        }, onFailure = { errorMessage ->
            Log.e(TAG, "decreaseGoodsCount: $errorMessage")
        })
    }

    private fun updateGoodsQuantity(
        goodsId: Int,
        transform: (Goods) -> Goods,
    ): Goods {
        val updatedList =
            goods.value.orEmpty().map { item ->
                if (item.id == goodsId) transform(item) else item
            }

        val updatedItem = updatedList.first { it.id == goodsId }

        _goods.value = updatedList
        return updatedItem
    }

    fun loadMoreGoods() {
        goodsRepository.getPagedGoods(page + 1, ITEM_COUNT, onSuccess = { moreGoods ->
            if (moreGoods.isNotEmpty()) {
                _goods.postValue(_goods.value?.plus(moreGoods))
                page = page.plus(1)
            } else {
                Log.e(TAG, "상품 로드 실패")
            }
            _shouldShowLoadMore.postValue(false)
        }, onFailure = { errorMessage ->
            Log.e(TAG, "loadMoreGoods: $errorMessage")
        })
    }

    fun updateShouldShowLoadMore() {
        goodsRepository.getPagedGoods(page + 1, ITEM_COUNT, onSuccess = {
            _shouldShowLoadMore.postValue(it.isNotEmpty())
        }, onFailure = { errorMessage ->
            Log.e(TAG, "updateShouldShowLoadMore: $errorMessage")
        })
    }

    fun updateShouldNavigateToShoppingCart() {
        _shouldNavigateToShoppingCart.setValue(Unit)
    }

    fun moveToDetail(
        goodsId: Int,
        move: (goodsId: Int, lastId: Int?) -> Unit,
    ) {
        latestGoodsRepository.getLast(onSuccess = { latestGoods ->
            updateLatestGoods(goodsId)
            move(goodsId, latestGoods?.goodsId)
        }, onFailure = { errorMessage ->
            Log.e(TAG, "moveToDetail: $errorMessage")
        })
    }

    private fun updateLatestGoods(goodsId: Int) {
        latestGoodsRepository.insertLatestGoods(goodsId, onSuccess = {
            setLatestGoods()
        }, onFailure = { errorMessage ->
            Log.e(TAG, "updateLatestGoods: $errorMessage")
        })
    }

    companion object {
        private const val TAG: String = "GoodsViewModel"
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
