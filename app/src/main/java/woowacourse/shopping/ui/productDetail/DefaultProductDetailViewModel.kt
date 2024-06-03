package woowacourse.shopping.ui.productDetail

import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class DefaultProductDetailViewModel(
    private val productId: Long,
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
) : ProductDetailViewModel() {
    override val uiState: ProductDetailUiState = DefaultProductDetailUiState()

    override val event: MutableSingleLiveData<ProductDetailEvent> = MutableSingleLiveData()

    override fun loadAll() {
        shoppingProductsRepository.loadProductAsyncResult(productId) { result ->
            result.onSuccess { product ->
                uiState.postCurrentProduct(product)
            }
            result.onFailure {
                // TODO: error
            }
        }

        productHistoryRepository.loadLatestProductIdAsyncResult { result ->
            result.onSuccess { latestProductId ->
                shoppingProductsRepository.loadProductAsyncResult(latestProductId) { latestProduct ->
                    latestProduct.onSuccess {
                        uiState.postLatestProduct(it)
                    }
                    latestProduct.onFailure {
                        // TODO: error
                    }

                }
            }
            result.onFailure {
            }
        }

        productHistoryRepository.saveProductHistoryAsyncResult(productId) { result ->
            result.onSuccess {
                // TODO: 내역에 저장됨
            }
            result.onFailure {
                // TODO: error
            }
        }
    }

    override fun addProductToCart() {
        shoppingProductsRepository.putItemInCartAsyncResult(productId, uiState.currentQuantity()) { result ->
            result.onSuccess {
                // TODO: 담겼다는 메시지
            }
            result.onFailure {
                // TODO: error
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

    override fun onClick(productId: Long) {
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
