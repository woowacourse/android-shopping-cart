package woowacourse.shopping.presentation.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.model.RemoveItemResult
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.cart.model.CartUiState
import woowacourse.shopping.presentation.cart.model.toUiModel
import kotlin.math.min

class CartViewModel(
    private val cartRepository: CartRepository = RepositoryProvider.cartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<CartEvent>(Channel.BUFFERED)
    val uiEvents: Flow<CartEvent> = _uiEvents.receiveAsFlow()
    private val pageSize = 5

    fun refreshCart() {
        viewModelScope.launch {
            loadCartItems()
        }
    }

    fun deleteItem(productId: Long) {
        viewModelScope.launch {
            val result = cartRepository.deleteItem(productId)
            when (result) {
                is RemoveItemResult.Success -> {
                    refreshCart()
                    _uiEvents.send(CartEvent.DeleteSuccess)
                }
                is RemoveItemResult.NotFoundItem -> {
                    _uiEvents.send(CartEvent.DeleteNotFound)
                }
            }
        }
    }

    fun increase(productId: Long) {
        viewModelScope.launch {
            cartRepository.addItem(productId)
            refreshCart()
        }
    }

    fun decrease(productId: Long) {
        viewModelScope.launch {
            cartRepository.decrease(productId)
            refreshCart()
        }
    }

    fun nextPage() {
        if (!uiState.value.isCanMoveNext) return
        _uiState.update { it.copy(page = it.page + 1) }
        viewModelScope.launch { refreshCart() }
    }

    fun previousPage() {
        if (uiState.value.page == 0) return
        _uiState.update {
            it.copy(page = it.page - 1)
        }
        viewModelScope.launch { refreshCart() }
    }

    private suspend fun loadCartItems() {
        if (uiState.value.isLoading) return
        _uiState.update {
            it.copy(isLoading = true)
        }

        try {
            val cart = cartRepository.getCart()
            val items = cart.items.map { it.toUiModel() }
            val maxPage = if (items.isEmpty()) 0 else (items.size - 1) / pageSize

            _uiState.update {
                val page = it.page.coerceIn(0, maxPage)
                val fromIndex = page * pageSize
                val toIndex = min(fromIndex + pageSize, items.size)
                it.copy(
                    page = page,
                    totalCartSize = items.size,
                    currentCartItems = items.subList(fromIndex, toIndex),
                    isCanMoveNext = toIndex < items.size,
                )
            }
        } finally {
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}

sealed interface CartEvent {
    data object DeleteSuccess : CartEvent

    data object DeleteNotFound : CartEvent
}
