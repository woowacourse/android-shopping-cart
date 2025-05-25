package woowacourse.shopping.presentation.goods.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.domain.repository.GoodsRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.ShoppingCartEvent
import woowacourse.shopping.presentation.util.SingleLiveData

class GoodsViewModel(
    private val goodsRepository: GoodsRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _items: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val items: LiveData<List<ShoppingCartItem>>
        get() = _items

    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoadMore: LiveData<Boolean>
        get() = _showLoadMore

    private val _shoppingCartEvent: MutableSingleLiveData<ShoppingCartEvent> =
        MutableSingleLiveData()
    val shoppingCartEvent: SingleLiveData<ShoppingCartEvent>
        get() = _shoppingCartEvent

    private var page: Int = DEFAULT_PAGE

    fun initGoods() {
        val currentItems = _items.value ?: loadGoods(page)
        shoppingCartRepository.getAllItems { result ->
            result.onSuccess { items ->
                updateQuantity(currentItems, items)
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    fun addGoods() {
        _showLoadMore.value = false
        loadNextPage()
    }

    fun determineLoadMoreVisibility(canScroll: Boolean) {
        val moreDataAvailable = goodsRepository.getPagedGoods(page, ITEM_COUNT).isNotEmpty()
        _showLoadMore.value = !canScroll && moreDataAvailable
    }

    fun increaseQuantity(item: ShoppingCartItem) {
        _items.value = _items.value?.map {
            if (it.goods.id == item.goods.id) {
                it.copy(quantity = it.quantity + 1)
            } else {
                it
            }
        }
    }

    fun decreaseQuantity(item: ShoppingCartItem) {
        _items.value = _items.value?.map {
            if (it.goods.id == item.goods.id && item.quantity > MINIMUM_VALUE) {
                it.copy(quantity = it.quantity - 1)
            } else {
                it
            }
        }
    }

    fun addToShoppingCart() {
        val currentItems = _items.value ?: return
        val mappedItems = currentItems.filter { it.quantity > MINIMUM_VALUE }

        shoppingCartRepository.addItems(mappedItems) { result ->
            result.onSuccess {
                _shoppingCartEvent.postValue(ShoppingCartEvent.SUCCESS)
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    private fun loadNextPage() {
        val newGoods = loadGoods(page++)
        _items.value = _items.value.orEmpty() + newGoods
    }

    private fun loadGoods(page: Int): List<ShoppingCartItem> {
        val goods = goodsRepository.getPagedGoods(page, ITEM_COUNT)
        return goods.map { ShoppingCartItem(it) }
    }

    private fun updateQuantity(
        currentItems: List<ShoppingCartItem>,
        selectedItems: List<ShoppingCartItem>,
    ) {
        val updatedItems = currentItems.map { item ->
            val selected = selectedItems.firstOrNull { it.goods.id == item.goods.id }
            item.copy(quantity = selected?.quantity ?: MINIMUM_VALUE)
        }
        _items.postValue(updatedItems)
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val ITEM_COUNT: Int = 20
        private const val MINIMUM_VALUE: Int = 0

        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                GoodsViewModel(
                    goodsRepository = RepositoryProvider.goodsRepository,
                    shoppingCartRepository = RepositoryProvider.shoppingCartRepository,
                )
            }
        }
    }
}
