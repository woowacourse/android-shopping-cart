package woowacourse.shopping.ui.cart

import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class DefaultShoppingCartViewModel(
    private val shoppingProductsRepository: ShoppingProductsRepository,
) : ShoppingCartViewModel() {
    override val uiState: DefaultShoppingCartUiState = DefaultShoppingCartUiState()

    private var _deletedItemId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val deletedItemId: SingleLiveData<Long> get() = _deletedItemId

    fun loadAll() {
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
            uiState.itemsInCurrentPage.postValue(products)
        }
        shoppingProductsRepository.isCartFinalPageAsync(nowPage) {
            uiState.isLastPage.postValue(it)
        }
    }

    fun nextPage() {
        if (uiState.isLastPage.value == true) return

        uiState.currentPage.value = uiState.currentPage.value?.plus(PAGE_MOVE_COUNT)
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.isCartFinalPageAsync(page = nowPage) {
            uiState.isLastPage.postValue(it)
        }
        shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
            uiState.itemsInCurrentPage.postValue(products)
        }
    }

    fun previousPage() {
        if (uiState.currentPage.value == FIRST_PAGE) return

        uiState.currentPage.value = uiState.currentPage.value?.minus(PAGE_MOVE_COUNT)
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.isCartFinalPageAsync(nowPage) {
            uiState.isLastPage.postValue(it)
        }
        shoppingProductsRepository.loadProductsInCartAsync(nowPage) { products ->
            uiState.itemsInCurrentPage.postValue(products)
        }
    }

    fun deleteItem(cartItemId: Long) {
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.removeShoppingCartProductAsync(cartItemId) {
            shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
                uiState.itemsInCurrentPage.postValue(products)
            }
            shoppingProductsRepository.isCartFinalPageAsync(nowPage) {
                uiState.isLastPage.postValue(it)

            }
        }
    }

    override fun onClick(productId: Long) {
        _deletedItemId.setValue(productId)
    }

    override fun onIncrease(productId: Long) {
        val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.increaseShoppingCartProductAsync(productId) {
            shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
                uiState.itemsInCurrentPage.postValue(products)

            }
        }
    }

    override fun onDecrease(productId: Long) {
        shoppingProductsRepository.loadProductAsync(productId) { product ->
            if (product.quantity == 1) return@loadProductAsync

            shoppingProductsRepository.decreaseShoppingCartProductAsync(productId) {
                val nowPage = uiState.currentPage.value ?: currentPageIsNullException()

                shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
                    uiState.itemsInCurrentPage.postValue(products)
                }
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
