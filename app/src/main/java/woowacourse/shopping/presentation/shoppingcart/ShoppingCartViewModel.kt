package woowacourse.shopping.presentation.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.util.event.MutableSingleLiveData
import woowacourse.shopping.presentation.util.event.SingleLiveData

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

    private val _onQuantityChanged: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val onQuantityChanged: SingleLiveData<Int>
        get() = _onQuantityChanged

    init {
        updateState()
    }

    private fun updateState() {
        shoppingRepository.getPagedGoods(_page.value ?: DEFAULT_PAGE_VALUE, ITEM_COUNT) { pagedGoods ->
            val quantityMap = pagedGoods.associateBy({ it.goodsId }, { it.goodsQuantity })

            goodsRepository.getGoodsListByIds(pagedGoods.map { it.goodsId }) { goods ->
                val updatedGoods =
                    goods.map { goodsItem ->
                        val quantity = quantityMap[goodsItem.id] ?: 0
                        goodsItem.copy(quantity = quantity)
                    }

                _goods.postValue(updatedGoods)
                updateNextPage()
                updatePreviousPage()
            }
        }
    }

    private fun updatePreviousPage() {
        _hasPreviousPage.postValue(_page.value != DEFAULT_PAGE_VALUE)
    }

    private fun updateNextPage() {
        shoppingRepository.getPagedGoods(
            _page.value?.plus(PAGE_CHANGE_AMOUNT) ?: DEFAULT_PAGE_VALUE,
            ITEM_COUNT,
        ) {
            _hasNextPage.postValue(it.isNotEmpty())
        }
    }

    fun increaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoods(goodsId) {
                it.increaseQuantity()
            }
        shoppingRepository.increaseGoodsQuantity(updatedItem.id) {}
    }

    fun decreaseGoodsCount(goodsId: Int) {
        val updatedItem =
            updateGoods(goodsId) {
                it.decreaseQuantity()
            }

        shoppingRepository.decreaseGoodsQuantity(updatedItem.id) {
            if (updatedItem.quantity == MINIMUM_QUANTITY) {
                updateState()
            }
        }
    }

    fun deleteGoods(goods: Goods) {
        shoppingRepository.removeGoods(goods.id) {
            updateState()
        }
    }

    private fun updateGoods(
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

    fun increasePage() {
        _page.value = _page.value?.plus(PAGE_CHANGE_AMOUNT)
        updateState()
    }

    fun decreasePage() {
        _page.value = _page.value?.minus(PAGE_CHANGE_AMOUNT)
        updateState()
    }

    companion object {
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
