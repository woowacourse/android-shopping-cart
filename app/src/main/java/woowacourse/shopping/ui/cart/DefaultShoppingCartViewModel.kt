package woowacourse.shopping.ui.cart

import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class DefaultShoppingCartViewModel(
    private val shoppingProductsRepository: ShoppingProductsRepository,
) : ShoppingCartViewModel() {
    override val uiState: ShoppingCartUiState = DefaultShoppingCartUiState()

    override val event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()

    override val error: MutableSingleLiveData<ShoppingCartError> = MutableSingleLiveData()

    override fun loadAll() {
        val nowPage = uiState.currentPage()

        loadProductsInCart(nowPage)
        calculateFinalPage(nowPage)
    }

    private fun loadProductsInCart(nowPage: Int) {
        shoppingProductsRepository.loadProductsInCartAsyncResult(page = nowPage) { result ->
            result.onSuccess { products ->
                uiState.postItemsInCurrentPage(products)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.LoadCart)
            }
        }
    }

    private fun calculateFinalPage(nowPage: Int) {
        shoppingProductsRepository.isCartFinalPageAsyncResult(nowPage) { result ->
            result.onSuccess {
                uiState.postIsLastPage(it)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.FinalPage)
            }
        }
    }

    override fun nextPage() {
        val nowPage = uiState.nextPage()

        loadProductsInCart(nowPage)
        calculateFinalPage(nowPage)
    }

    override fun previousPage() {
        val nowPage = uiState.previousPage()

        loadProductsInCart(nowPage)
        calculateFinalPage(nowPage)
    }

    override fun deleteItem(cartItemId: Long) {
        val nowPage = uiState.currentPage()

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

    override fun onBackClick() {
        event.setValue(ShoppingCartEvent.NavigateToProductList)
    }

    override fun onClick(productId: Long) {
        event.setValue(ShoppingCartEvent.DeleteItem(productId))
    }

    override fun onIncrease(productId: Long) {
        val nowPage = uiState.currentPage()

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
                val nowPage = uiState.currentPage()

                loadProductsInCart(nowPage)
                calculateFinalPage(nowPage)
            }
            result.onFailure {
                error.setValue(ShoppingCartError.UpdateItemQuantity)
            }
        }
    }

    companion object {
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
