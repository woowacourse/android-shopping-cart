package woowacourse.shopping.ui.productDetail

import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.UniversalViewModelFactory
import woowacourse.shopping.ui.productDetail.event.ProductDetailError
import woowacourse.shopping.ui.productDetail.event.ProductDetailEvent
import woowacourse.shopping.ui.util.MutableSingleLiveData

class DefaultProductDetailViewModel(
    private val productId: Long,
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ProductDetailViewModel() {
    override val uiState: ProductDetailUiState = DefaultProductDetailUiState()

    override val event: MutableSingleLiveData<ProductDetailEvent> = MutableSingleLiveData()

    override val error: MutableSingleLiveData<ProductDetailError> = MutableSingleLiveData()

    override fun loadAll() {
        loadCurrentProduct()
        loadLatestProduct()
        saveCurrentProductInHistory()
    }

    private fun loadCurrentProduct() {
        shoppingProductsRepository.loadProductAsyncResult(productId) { result ->
            result.onSuccess { product ->
                uiState.postCurrentProduct(product)
            }
            result.onFailure {
                error.postValue(ProductDetailError.LoadProduct)
            }
        }
    }

    private fun loadLatestProduct() {
        productHistoryRepository.loadLatestProductIdAsyncResult { result ->
            result.onSuccess { latestProductId ->
                loadLatestProductWithId(latestProductId)
            }
            result.onFailure {
                error.postValue(ProductDetailError.LoadLatestProduct)
            }
        }
    }

    private fun loadLatestProductWithId(latestProductId: Long) {
        shoppingProductsRepository.loadProductAsyncResult(latestProductId) { latestProduct ->
            latestProduct.onSuccess {
                uiState.postLatestProduct(it)
            }
            latestProduct.onFailure {
                error.postValue(ProductDetailError.LoadProduct)
            }
        }
    }

    private fun saveCurrentProductInHistory() {
        productHistoryRepository.saveProductHistoryAsyncResult(productId) { result ->
            result.onFailure {
                error.postValue(ProductDetailError.SaveProductInHistory)
            }
        }
    }

    override fun addProductToCart() {
        shoppingProductsRepository.putItemInCartAsyncResult(productId, uiState.currentQuantity()) { result ->
            result.onSuccess {
                event.postValue(ProductDetailEvent.AddProductToCart)
            }
            result.onFailure {
                error.postValue(ProductDetailError.AddProductToCart)
            }
        }
    }

    override fun onFinishClick() {
        event.setValue(ProductDetailEvent.NavigateToProductList)
    }

    override fun onIncrease(productId: Long) {
        uiState.increaseProductCount()
    }

    override fun onDecrease(productId: Long) {
        uiState.decreaseProductCount()
    }

    override fun navigateToDetail(productId: Long) {
        event.setValue(ProductDetailEvent.NavigateToProductDetail(productId))
    }

    companion object {
        private const val TAG = "ProductDetailViewModel"

        fun factory(
            productId: Long,
            shoppingProductsRepository: ShoppingProductsRepository =
                DefaultShoppingProductRepository(
                    productsSource = ShoppingApp.productSource,
                    cartSource = ShoppingApp.cartSource,
                ),
            historyRepository: ProductHistoryRepository =
                DefaultProductHistoryRepository(
                    productHistoryDataSource = ShoppingApp.historySource,
                    productDataSource = ShoppingApp.productSource,
                ),
        ): UniversalViewModelFactory {
            return UniversalViewModelFactory {
                DefaultProductDetailViewModel(
                    productId = productId,
                    shoppingProductsRepository = shoppingProductsRepository,
                    productHistoryRepository = historyRepository,
                )
            }
        }
    }
}
