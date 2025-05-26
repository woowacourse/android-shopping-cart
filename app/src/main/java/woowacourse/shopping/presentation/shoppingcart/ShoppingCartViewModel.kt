package woowacourse.shopping.presentation.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.ShoppingCartEvent
import woowacourse.shopping.presentation.util.SingleLiveData

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) : ViewModel() {
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

    private val _shoppingCartEvent: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val shoppingCartEvent: SingleLiveData<ShoppingCartEvent>
        get() = _shoppingCartEvent

    init {
        updateState()
    }

    fun increaseQuantity(item: ShoppingCartItem) {
        _items.value = _items.value?.map {
            if (it.goods.id == item.goods.id) {
                val updated = it.increaseQuantity()
                updateQuantity(updated)
                updated
            } else {
                it
            }
        }
    }

    fun decreaseQuantity(item: ShoppingCartItem) {
        _items.value = _items.value?.mapNotNull {
            if (it.goods.id == item.goods.id) {
                val updated = it.decreaseQuantity()
                if (updated.quantity > MINIMUM_VALUE) {
                    updateQuantity(updated)
                    updated
                } else {
                    deleteItem(it)
                    null
                }
            } else {
                it
            }
        }
    }

    fun deleteItem(item: ShoppingCartItem) {
        repository.removeItem(item) { result ->
            result.onSuccess {
                _shoppingCartEvent.postValue(ShoppingCartEvent.SUCCESS)
                updateState()
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
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
        val currentPage = _page.value ?: DEFAULT_PAGE_VALUE
        repository.getPagedItems(currentPage, ITEM_COUNT) { result ->
            result.onSuccess { items ->
                if (items.isEmpty() && currentPage > DEFAULT_PAGE_VALUE) {
                    _page.postValue(currentPage - PAGE_CHANGE_AMOUNT)
                    updateState()
                    return@onSuccess
                }
                _items.postValue(items)
                updateNextPage()
                updatePreviousPage()
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    private fun updatePreviousPage() {
        _hasPreviousPage.postValue(_page.value != DEFAULT_PAGE_VALUE)
    }

    private fun updateNextPage() {
        repository.getPagedItems(
            _page.value?.plus(PAGE_CHANGE_AMOUNT) ?: DEFAULT_PAGE_VALUE,
            ITEM_COUNT
        ) { result ->
            result.onSuccess { items ->
                _hasNextPage.postValue(items.isNotEmpty())
            }.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    private fun updateQuantity(item: ShoppingCartItem) {
       repository.upsertItem(item) { result ->
            result.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    private fun removeItem(item: ShoppingCartItem) {
        repository.removeItem(item) { result ->
            result.onFailure {
                _shoppingCartEvent.postValue(ShoppingCartEvent.FAILURE)
            }
        }
    }

    companion object {
        private const val ITEM_COUNT: Int = 5
        private const val DEFAULT_PAGE_VALUE: Int = 0
        private const val PAGE_CHANGE_AMOUNT: Int = 1
        private const val MINIMUM_VALUE: Int = 0

        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShoppingCartViewModel(RepositoryProvider.shoppingCartRepository)
            }
        }
    }
}
