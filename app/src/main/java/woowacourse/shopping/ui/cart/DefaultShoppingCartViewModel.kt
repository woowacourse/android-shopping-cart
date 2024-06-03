package woowacourse.shopping.ui.cart

import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class DefaultShoppingCartViewModel(
    private val shoppingProductsRepository: ShoppingProductsRepository,
) : ShoppingCartViewModel() {
    override val uiState: DefaultShoppingCartUiState = DefaultShoppingCartUiState()

    override val event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()

    override val error: MutableSingleLiveData<ShoppingCartError> = MutableSingleLiveData()

    override fun loadAll() {
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        loadProductsInCart(nowPage)
        calculateFinalPage(nowPage)
    }

    private fun loadProductsInCart(nowPage: Int) {
        shoppingProductsRepository.loadProductsInCartAsyncResult(page = nowPage) { result ->
            result.onSuccess { products ->
                uiState.itemsInCurrentPage.postValue(products)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.LoadCart)
            }
        }
    }

    private fun calculateFinalPage(nowPage: Int) {
        shoppingProductsRepository.isCartFinalPageAsyncResult(nowPage) { result ->
            result.onSuccess {
                uiState.isLastPage.postValue(it)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.FinalPage)
            }
        }
    }

    override fun nextPage() {
        if (uiState.isLastPage.value == true) return

        uiState.currentPage.value = uiState.currentPage.value?.plus(PAGE_MOVE_COUNT)
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        loadProductsInCart(nowPage)
        calculateFinalPage(nowPage)
    }

    override fun previousPage() {
        if (uiState.currentPage.value == FIRST_PAGE) return

        uiState.currentPage.value = uiState.currentPage.value?.minus(PAGE_MOVE_COUNT)
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        loadProductsInCart(nowPage)
        calculateFinalPage(nowPage)
    }

    override fun deleteItem(cartItemId: Long) {
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.removeShoppingCartProductAsyncResult(cartItemId) { result ->
            result.onSuccess {
                loadProductsInCart(nowPage)
                calculateFinalPage(nowPage)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.DeleteItem)
            }
        }
    }

    override fun onClick(productId: Long) {
        event.setValue(ShoppingCartEvent.DeleteItem(productId))
    }

    override fun onIncrease(productId: Long) {
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.increaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                loadProductsInCart(nowPage)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.UpdateItemQuantity)
            }
        }
    }

    override fun onDecrease(productId: Long) {
        shoppingProductsRepository.decreaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

                loadProductsInCart(nowPage)
                calculateFinalPage(nowPage)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.UpdateItemQuantity)
            }
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
        private const val TAG = "ShoppingCartViewModel"

        fun factory(
            shoppingProductsRepository: ShoppingProductsRepository =
                DefaultShoppingProductRepository(
                    productsSource = ShoppingApp.productSource,
                    cartSource = ShoppingApp.cartSource,
                ),
        ): UniversalViewModelFactory =
            UniversalViewModelFactory {
                DefaultShoppingCartViewModel(shoppingProductsRepository)
            }
    }
}
