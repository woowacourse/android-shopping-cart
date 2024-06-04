package woowacourse.shopping.ui.productList

import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.UniversalViewModelFactory
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListEvent
import woowacourse.shopping.ui.util.MutableSingleLiveData

class DefaultProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ProductListViewModel() {
    override val uiState: ProductListUiState = DefaultProductListUiState()
    override val errorEvent: MutableSingleLiveData<ProductListError> = MutableSingleLiveData()
    override val navigationEvent: MutableSingleLiveData<ProductListEvent> = MutableSingleLiveData()

    override fun loadAll() {
        val page = uiState.currentPage()
        loadAllProducts(page)
        calculateFinalPage(page)
        calculateProductsQuantityInCart()
        loadProductsHistory()
    }

    private fun loadAllProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult2(page) { result ->
            result.onSuccess { products ->
                uiState.postLoadedProducts(products)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    private fun calculateFinalPage(page: Int) {
        productsRepository.isFinalPageAsyncResult(page) { result ->
            result.onSuccess { isLastPage ->
                uiState.postLastPage(isLastPage)
            }.onFailure {
                errorEvent.postValue(ProductListError.FinalPage)
            }
        }
    }

    private fun calculateProductsQuantityInCart() {
        productsRepository.shoppingCartProductQuantityAsyncResult { result ->
            result.onSuccess {
                uiState.postCartProductTotalCount(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.CartProductQuantity)
            }
        }
    }

    private fun loadProductsHistory() {
        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                uiState.postProductsHistory(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProductHistory)
            }
        }
    }

    override fun loadNextPageProducts() {
        val nextPage = uiState.nextPage()

        calculateFinalPage(nextPage)
        addNextPageProducts(nextPage)
        calculateProductsQuantityInCart()
    }

    private fun addNextPageProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess { products ->
                uiState.addLoadedProducts(products)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    override fun navigateToShoppingCart() {
        navigationEvent.setValue(ProductListEvent.NavigateToShoppingCart)
    }

    override fun navigateToDetail(id: Long) {
        navigationEvent.setValue(ProductListEvent.NavigateToProductDetail(id))
    }

    override fun onAdd(productId: Long) {
        productsRepository.addShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                uiState.increaseProductQuantity(productId)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.AddProductInCart)

            }
        }
    }

    override fun onIncrease(productId: Long) {
        productsRepository.increaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                uiState.increaseProductQuantity(productId)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.UpdateProductQuantity)

            }
        }
    }

    override fun onDecrease(productId: Long) {
        productsRepository.decreaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                uiState.decreaseProductQuantity(productId)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.UpdateProductQuantity)
            }
        }
    }

    companion object {
        private const val TAG = "ProductListViewModel"

        fun factory(
            productRepository: ShoppingProductsRepository =
                DefaultShoppingProductRepository(
                    ShoppingApp.productSource,
                    ShoppingApp.cartSource,
                ),
            historyRepository: ProductHistoryRepository =
                DefaultProductHistoryRepository(
                    ShoppingApp.historySource,
                    ShoppingApp.productSource,
                ),
        ): UniversalViewModelFactory =
            UniversalViewModelFactory {
                DefaultProductListViewModel(productRepository, historyRepository)
            }
    }
}
