package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.ShoppingCartPagingSource
import kotlin.concurrent.thread

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) :
    BaseViewModel(),
    ShoppingCartActionHandler {
    private val _uiState: MutableLiveData<ShoppingCartUiState> =
        MutableLiveData(ShoppingCartUiState())
    val uiState: LiveData<ShoppingCartUiState> get() = _uiState

    private val shoppingCartPagingSource = ShoppingCartPagingSource(repository)

    init {
        loadCartProducts(INIT_PAGE)
    }

    private fun loadCartProducts(page: Int) {
        thread {
            shoppingCartPagingSource.load(page).onSuccess { pagingCartProduct ->
                _uiState.postValue(_uiState.value?.copy(pagingCartProduct = pagingCartProduct))
                println(123124123)
                println(_uiState.value.toString())
            }.onFailure {
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    override fun deleteCartProduct(productId: Long) {
        thread {
            repository.deleteCartProduct(productId = productId).onSuccess {
                uiState.value?.let { state ->
                    loadCartProducts(state.pagingCartProduct.currentPage)
                }
            }.onFailure {
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    fun loadNextPage() {
        uiState.value?.let { state ->
            loadCartProducts(state.pagingCartProduct.currentPage + 1)
        }
    }

    fun loadPreviousPage() {
        uiState.value?.let { state ->
            loadCartProducts(state.pagingCartProduct.currentPage - 1)
        }
    }

    companion object {
        const val INIT_PAGE = 0

        fun factory(repository: ShoppingCartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ShoppingCartViewModel(repository) }
        }
    }
}
