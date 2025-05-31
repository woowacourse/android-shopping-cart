package woowacourse.shopping.presentation.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository

class ShoppingCartViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _goods: MutableLiveData<List<Goods>> = MutableLiveData()
    val goods: LiveData<List<Goods>>
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

    init {
        updateState()
    }

    private fun updateState() {
        shoppingRepository.getPagedGoods(
            _page.value ?: DEFAULT_PAGE_VALUE,
            ITEM_COUNT,
            onSuccess = { pagedGoods ->
                val quantityMap = pagedGoods.associateBy({ it.goodsId }, { it.goodsQuantity })

                goodsRepository.getGoodsListByIds(
                    pagedGoods.map { it.goodsId },
                    onSuccess = { goods ->
                        val updatedGoods =
                            goods.map { goodsItem ->
                                val quantity = quantityMap[goodsItem.id] ?: 0
                                goodsItem.copy(quantity = quantity)
                            }

                        _goods.postValue(updatedGoods)
                        updateNextPage()
                        updatePreviousPage()
                    },
                    onFailure = { errorMessage ->
                        Log.e(TAG, "updateState: $errorMessage")
                    },
                )
            },
            onFailure = { errorMessage ->
                Log.e(TAG, "updateState: $errorMessage")
            },
        )
    }

    private fun updatePreviousPage() {
        _hasPreviousPage.postValue(_page.value != DEFAULT_PAGE_VALUE)
    }

    private fun updateNextPage() {
        shoppingRepository.getPagedGoods(
            _page.value?.plus(PAGE_CHANGE_AMOUNT) ?: DEFAULT_PAGE_VALUE,
            ITEM_COUNT,
            onSuccess = {
                _hasNextPage.postValue(it.isNotEmpty())
            },
            onFailure = { errorMessage ->
                Log.e(TAG, "updateNextPage: $errorMessage")
            },
        )
    }

    fun increaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoods(goodsId) {
                it.increaseQuantity()
            }
        shoppingRepository.increaseGoodsQuantity(
            updatedItem.id,
            onSuccess = {},
            onFailure = { errorMessage ->
                Log.e(TAG, "increaseGoodsCount: $errorMessage")
            },
        )
    }

    fun decreaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoods(goodsId) {
                it.decreaseQuantity()
            }

        shoppingRepository.decreaseGoodsQuantity(updatedItem.id, onSuccess = {
            if (updatedItem.quantity == MINIMUM_QUANTITY) {
                updateState()
            }
        }, onFailure = { errorMessage ->
            Log.e(TAG, "decreaseGoodsCount: $errorMessage")
        })
    }

    fun deleteGoods(goods: Goods) {
        shoppingRepository.removeGoods(goods.id, onSuccess = {
            updateState()
        }, onFailure = { errorMessage ->
            Log.e(TAG, "deleteGoods: $errorMessage")
        })
    }

    private fun updateGoods(
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

    fun increasePage() {
        _page.value = _page.value?.plus(PAGE_CHANGE_AMOUNT)
        updateState()
    }

    fun decreasePage() {
        _page.value = _page.value?.minus(PAGE_CHANGE_AMOUNT)
        updateState()
    }

    companion object {
        private const val TAG: String = "ShoppingCartViewModel"
        private const val ITEM_COUNT: Int = 5
        private const val DEFAULT_PAGE_VALUE: Int = 0
        private const val PAGE_CHANGE_AMOUNT: Int = 1
        private const val MINIMUM_QUANTITY: Int = 0

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
