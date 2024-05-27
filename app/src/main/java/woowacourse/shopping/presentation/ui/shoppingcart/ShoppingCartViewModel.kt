package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.ShoppingCartPagingSource
import kotlin.concurrent.thread

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) :
    BaseViewModel(),
    ShoppingCartActionHandler,
    ProductCountHandler {
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
                hideError()
                _uiState.postValue(_uiState.value?.copy(pagingCartProduct = pagingCartProduct))
            }.onFailure { e ->
                showError(e)
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    override fun retry() {
        _uiState.value?.let { state ->
            loadCartProducts(state.pagingCartProduct.currentPage)
        }
    }

    override fun plusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        updateProductQuantity(productId = productId, increment = true)
    }

    override fun minusProductQuantity(
        productId: Long,
        position: Int,
    ) {
        updateProductQuantity(productId = productId, increment = false)
    }

    private fun updateProductQuantity(
        productId: Long,
        increment: Boolean,
    ) {
        _uiState.value?.let { state ->
            val updatedProductList =
                state.pagingCartProduct.products.map { product ->
                    if (product.id == productId) {
                        product.updateProduct(increment)
                    } else {
                        product
                    }
                }
            val pagingCartProduct =
                PagingCartProduct(
                    products = updatedProductList,
                    last = state.pagingCartProduct.last,
                )
            _uiState.postValue(
                state.copy(pagingCartProduct = pagingCartProduct),
            )
        }
    }

    private fun Product.updateProduct(increment: Boolean): Product {
        val updatedQuantity = if (increment) this.quantity + 1 else this.quantity - 1
        when {
            this.quantity == 0 -> insertCartProduct(this, updatedQuantity)
            updatedQuantity == 0 -> deleteCartProduct(this.id)
            else -> updateCartProduct(this.id, updatedQuantity)
        }
        return this.copy(quantity = updatedQuantity)
    }

    private fun insertCartProduct(
        product: Product,
        quantity: Int,
    ) {
        thread {
            repository.insertCartProduct(
                productId = product.id,
                name = product.name,
                price = product.price,
                quantity = quantity,
                imageUrl = product.imageUrl,
            ).onSuccess {
                hideError()
            }.onFailure { e ->
                showError(e)
            }
        }
    }

    override fun deleteCartProduct(productId: Long) {
        thread {
            repository.deleteCartProduct(productId = productId).onSuccess {
                uiState.value?.let { state ->
                    loadCartProducts(state.pagingCartProduct.currentPage)
                }
            }.onSuccess {
                hideError()
            }.onFailure { e ->
                showError(e)
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    private fun updateCartProduct(
        productId: Long,
        quantity: Int,
    ) {
        thread {
            repository.updateCartProduct(
                productId = productId,
                quantity = quantity,
            ).onSuccess {
                hideError()
            }.onFailure { e ->
                showError(e)
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
