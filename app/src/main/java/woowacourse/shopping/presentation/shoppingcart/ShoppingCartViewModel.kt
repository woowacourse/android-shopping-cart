package woowacourse.shopping.presentation.shoppingcart

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

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val goodsRepository: GoodsRepository,
) : ViewModel() {
    private val _items: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val items: LiveData<List<ShoppingCartItem>>
        get() = _items

    private val _page: MutableLiveData<Int> = MutableLiveData(DEFAULT_PAGE_VALUE)
    val page: LiveData<Int>
        get() = _page

    private val _hasNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val hasNextPage: LiveData<Boolean>
        get() = _hasNextPage

    private val _hasPreviousPage: MutableLiveData<Boolean> = MutableLiveData()
    val hasPreviousPage: LiveData<Boolean>
        get() = _hasPreviousPage

    private val _shoppingCartEvent: MutableSingleLiveData<ShoppingCartEvent> =
        MutableSingleLiveData()
    val shoppingCartEvent: SingleLiveData<ShoppingCartEvent>
        get() = _shoppingCartEvent

    init {
        updateState()
    }

    fun increaseQuantity(target: ShoppingCartItem) {
        updateItems(target) { it.increaseQuantity() }
    }

    fun decreaseQuantity(target: ShoppingCartItem) {
        updateItems(target) {
            val decreased = it.decreaseQuantity()
            if (decreased.quantity <= MINIMUM_VALUE) {
                deleteItem(it)
                null
            } else decreased
        }
    }

    fun deleteItem(item: ShoppingCartItem) {
        shoppingCartRepository.removeItem(item) { result ->
            result
                .onSuccess {
                    postEvent(ShoppingCartEvent.SUCCESS)
                    updateState()
                }
                .onFailure { postEvent(ShoppingCartEvent.FAILURE) }
        }
    }

    fun increasePage() {
        updatePage { it + PAGE_CHANGE_AMOUNT }
    }

    fun decreasePage() {
        updatePage { it - PAGE_CHANGE_AMOUNT }
    }

    private fun updateItems(
        target: ShoppingCartItem,
        transform: (ShoppingCartItem) -> ShoppingCartItem?,
    ) {
        _items.value = _items.value?.mapNotNull { item ->
            if (item.goods.id == target.goods.id) {
                transform(item)?.also {
                    updateQuantity(it)
                }
            } else item
        }
    }

    private fun updateQuantity(item: ShoppingCartItem) {
        shoppingCartRepository.saveItem(item) { result ->
            result.onFailure {
                postEvent(ShoppingCartEvent.FAILURE)
            }
        }
    }

    private fun updatePage(transform: (Int) -> Int) {
        _page.value = transform(_page.value ?: DEFAULT_PAGE_VALUE)
        updateState()
    }

    private fun updateState() {
        val currentPage = _page.value ?: DEFAULT_PAGE_VALUE

        shoppingCartRepository.getPagedItems(currentPage, ITEM_COUNT) { result ->
            result
                .onSuccess { items ->
                    if (items.isEmpty() && currentPage > DEFAULT_PAGE_VALUE) {
                        _page.postValue(currentPage - PAGE_CHANGE_AMOUNT)
                        updateState()
                        return@onSuccess
                    }

                    val enrichedItems = items.map {
                        val latestGoods = goodsRepository.getGoodsById(it.goods.id)
                        it.copy(goods = latestGoods)
                    }

                    _items.postValue(enrichedItems)
                    updatePaginationStates()
                }
                .onFailure { postEvent(ShoppingCartEvent.FAILURE) }
        }
    }

    private fun updatePaginationStates() {
        val currentPage = _page.value ?: DEFAULT_PAGE_VALUE
        _hasPreviousPage.postValue(currentPage != DEFAULT_PAGE_VALUE)

        shoppingCartRepository.getPagedItems(
            currentPage + PAGE_CHANGE_AMOUNT,
            ITEM_COUNT
        ) { result ->
            result
                .onSuccess { _hasNextPage.postValue(it.isNotEmpty()) }
                .onFailure { postEvent(ShoppingCartEvent.FAILURE) }
        }
    }

    private fun postEvent(event: ShoppingCartEvent) {
        _shoppingCartEvent.postValue(event)
    }

    companion object {
        private const val ITEM_COUNT: Int = 5
        private const val DEFAULT_PAGE_VALUE: Int = 0
        private const val PAGE_CHANGE_AMOUNT: Int = 1
        private const val MINIMUM_VALUE: Int = 0

        val FACTORY: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ShoppingCartViewModel(
                        shoppingCartRepository = RepositoryProvider.shoppingCartRepository,
                        goodsRepository = RepositoryProvider.goodsRepository,
                    )
                }
            }
    }
}
