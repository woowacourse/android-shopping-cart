package woowacourse.shopping.ui.productList

import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListNavigationEvent

class DefaultProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ProductListViewModel() {
    override val uiState: DefaultProductListUiState = DefaultProductListUiState()

    override val errorEvent: MutableSingleLiveData<ProductListError> = MutableSingleLiveData()

    override val navigationEvent: MutableSingleLiveData<ProductListNavigationEvent> = MutableSingleLiveData()

    override fun loadAll() {
        val page = uiState.currentPage.value ?: currentPageIsNullException()
        loadAllProducts(page)
        loadFinalPage(page)
        calculateProductsQuantityInCart()
        loadProductsHistory()
    }

    private fun loadAllProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess {
                uiState.loadedProducts.postValue(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    private fun loadFinalPage(page: Int) {
        productsRepository.isFinalPageAsyncResult(page) { result ->
            result.onSuccess {
                uiState.isLastPage.postValue(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.FinalPage)
            }
        }
    }

    private fun calculateProductsQuantityInCart() {
        productsRepository.shoppingCartProductQuantityAsyncResult { result ->
            result.onSuccess {
                uiState.cartProductTotalCount.postValue(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.CartProductQuantity)
            }
        }
    }

    private fun loadProductsHistory() {
        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                uiState.productsHistory.postValue(it)
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProductHistory)
            }
        }
    }

    override fun loadNextPageProducts() {
        if (uiState.isLastPage.value == true) return

        val nextPage = uiState.currentPage.value?.plus(PAGE_MOVE_COUNT) ?: currentPageIsNullException()
        uiState.currentPage.postValue(nextPage)

        loadFinalPage(nextPage)
        addNextPageProducts(nextPage)
        calculateProductsQuantityInCart()
    }

    private fun addNextPageProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess {
                uiState.loadedProducts.postValue(uiState.loadedProducts.value?.toMutableList()?.apply { addAll(it) })
            }.onFailure {
                errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    override fun navigateToShoppingCart() {
        navigationEvent.setValue(ProductListNavigationEvent.ShoppingCart)
    }

    override fun onClick(productId: Long) {
        navigationEvent.setValue(ProductListNavigationEvent.ProductDetail(productId))
    }

    override fun onAdd(productId: Long) {
        productsRepository.addShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                changeProductQuantity(productId, INCREASE_AMOUNT)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.AddProductInCart)

            }
        }
    }

    override fun onIncrease(productId: Long) {
        productsRepository.increaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                changeProductQuantity(productId, INCREASE_AMOUNT)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.UpdateProductQuantity)

            }
        }
    }

    override fun onDecrease(productId: Long) {
        productsRepository.decreaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                changeProductQuantity(productId, DECREASE_AMOUNT)
                calculateProductsQuantityInCart()
            }.onFailure {
                errorEvent.postValue(ProductListError.UpdateProductQuantity)
            }
        }
    }

    private fun changeProductQuantity(
        productId: Long,
        changeAmount: Int,
    ) {
        uiState.loadedProducts.postValue(
            uiState.loadedProducts.value?.map {
                if (it.id == productId) {
                    it.copy(quantity = it.quantity + changeAmount)
                } else {
                    it
                }
            },
        )
    }

    companion object {
        private const val TAG = "ProductListViewModel"
        private const val PAGE_MOVE_COUNT = 1
        private const val INCREASE_AMOUNT = 1
        private const val DECREASE_AMOUNT = -1

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
