package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.CartQuantityActionHandler

class ShoppingViewModel(
    private val shoppingRepository: ShoppingItemsRepository,
    private val cartRepository: CartRepository,
    private val recentlyViewedProductsRepository: RecentlyViewedProductsRepository,
) : ViewModel(), ShoppingActionHandler, CartQuantityActionHandler {
    private val _currentPage = MutableLiveData<Int>()
    private val currentPage: LiveData<Int> = _currentPage

    private val _isProductListEmpty = MutableLiveData(false)
    val isProductListEmpty: LiveData<Boolean> = _isProductListEmpty

    private val _isRecentlyViewedEmpty = MutableLiveData(false)
    val isRecentlyViewedEmpty: LiveData<Boolean> = _isRecentlyViewedEmpty

    private val _navigateToProductDetail = MutableLiveData<Long>()
    val navigateToProductDetail: LiveData<Long> = _navigateToProductDetail

    private val _totalCartItemsCount = MutableLiveData<Int>()
    val totalCartItemsCount: LiveData<Int> = _totalCartItemsCount

    private val _updatedProduct = MutableLiveData<UIState<ProductWithQuantity>>()
    val updatedProduct: LiveData<UIState<ProductWithQuantity>> = _updatedProduct

    private val _recentlyViewedProductsState =
        MutableLiveData<UIState<List<RecentlyViewedProduct>>>()
    val recentlyViewedProductsState: LiveData<UIState<List<RecentlyViewedProduct>>> =
        _recentlyViewedProductsState

    val productItemsState: LiveData<UIState<List<ProductWithQuantity>>> =
        currentPage.switchMap { page ->
            MutableLiveData<UIState<List<ProductWithQuantity>>>().apply {
                value =
                    try {
                        val productList =
                            shoppingRepository.findProductWithQuantityItemsByPage(page, PAGE_SIZE)
                        if (productList.isEmpty()) {
                            _isProductListEmpty.postValue(true)
                            UIState.Empty
                        } else {
                            UIState.Success(productList)
                        }
                    } catch (e: Exception) {
                        UIState.Error(e)
                    }
            }
        }

    init {
        loadProducts()
        loadRecentlyViewedProducts()
    }

    private fun loadProducts() {
        _currentPage.postValue(0)
        _totalCartItemsCount.postValue(cartRepository.sumQuantity())
    }

    fun loadRecentlyViewedProducts() {
        try {
            val recentlyViewedProducts =
                recentlyViewedProductsRepository.getRecentlyViewedProducts(
                    RECENTLY_VIEWED_PRODUCT_SIZE,
                )
            if (recentlyViewedProducts.isEmpty()) {
                _isRecentlyViewedEmpty.postValue(true)
                _recentlyViewedProductsState.postValue(UIState.Empty)
            } else {
                _recentlyViewedProductsState.postValue(UIState.Success(recentlyViewedProducts))
            }
        } catch (e: Exception) {
            _recentlyViewedProductsState.postValue(UIState.Error(e))
        }
    }

    override fun onProductClick(productId: Long) {
        _navigateToProductDetail.postValue(productId)
    }

    override fun onLoadMoreButtonClick() {
        val nextPage = (_currentPage.value ?: 0) + 1
        _currentPage.value = nextPage
    }

    fun updateModifiedProducts(modifiedProductIds: List<Long>) {
        for (productId in modifiedProductIds) {
            updateProductWithAction(productId) {}
        }
    }

    private fun updateProductWithAction(
        productId: Long,
        action: () -> Unit,
    ) {
        try {
            action()
            val productWithQuantityItem =
                shoppingRepository.productWithQuantityItem(productId)
                    ?: throw Exception(PRODUCT_NOT_FOUND)
            _updatedProduct.postValue(UIState.Success(productWithQuantityItem))
            _totalCartItemsCount.postValue(cartRepository.sumQuantity())
        } catch (e: Exception) {
            _updatedProduct.postValue(UIState.Error(e))
        }
    }

    override fun onAddToCartButtonClick(productId: Long) {
        updateProductWithAction(productId) {
            shoppingRepository.productWithQuantityItem(productId)?.let {
                cartRepository.insert(it)
            }
        }
    }

    override fun onPlusButtonClicked(productId: Long) {
        updateProductWithAction(productId) {
            cartRepository.plusQuantity(productId, 1)
        }
    }

    override fun onMinusButtonClicked(productId: Long) {
        updateProductWithAction(productId) {
            cartRepository.minusQuantity(productId, 1)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val RECENTLY_VIEWED_PRODUCT_SIZE = 10
        private const val PRODUCT_NOT_FOUND = "상품이 존재하지 않습니다."
    }
}
