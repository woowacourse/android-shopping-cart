package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.CartQuantityActionHandler

class ShoppingViewModel(
    private val shoppingRepository: ShoppingItemsRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ShoppingActionHandler, CartQuantityActionHandler {
    private val _currentPage = MutableLiveData(0)
    private val currentPage: LiveData<Int> = _currentPage

    private val _isProductListEmpty = MutableLiveData(false)
    val isProductListEmpty: LiveData<Boolean> = _isProductListEmpty

    private val _navigateToProductDetail = MutableLiveData<Long>()
    val navigateToProductDetail: LiveData<Long> = _navigateToProductDetail

    private val _updatedProduct = MutableLiveData<UIState<ProductWithQuantity>>()
    val updatedProduct: LiveData<UIState<ProductWithQuantity>> = _updatedProduct

    val productItemsState: LiveData<UIState<List<ProductWithQuantity>>> =
        currentPage.switchMap { page ->
            MutableLiveData<UIState<List<ProductWithQuantity>>>().apply {
                value =
                    try {
                        val productList = shoppingRepository.findProductWithQuantityItemsByPage(page, PAGE_SIZE)
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
    }

    private fun loadProducts() {
        _currentPage.postValue(0)
    }

    override fun onProductClick(productId: Long) {
        _navigateToProductDetail.postValue(productId)
    }

    override fun onLoadMoreButtonClick() {
        val nextPage = (_currentPage.value ?: 0) + 1
        _currentPage.value = nextPage
    }

    private fun updateProductWithAction(
        productId: Long,
        action: (ProductWithQuantity) -> Unit,
    ) {
        try {
            val product =
                shoppingRepository.productWithQuantityItem(productId)
                    ?: throw Exception(PRODUCT_NOT_FOUND)
            action(product)
            _updatedProduct.postValue(UIState.Success(product))
        } catch (e: Exception) {
            _updatedProduct.postValue(UIState.Error(e))
        }
    }

    override fun onAddToCartButtonClick(productId: Long) {
        updateProductWithAction(productId) { product ->
            cartRepository.insert(product)
        }
    }

    override fun onPlusButtonClicked(productId: Long) {
        updateProductWithAction(productId) { _ ->
            cartRepository.plusQuantity(productId, 1)
        }
    }

    override fun onMinusButtonClicked(productId: Long) {
        updateProductWithAction(productId) { _ ->
            cartRepository.minusQuantity(productId, 1)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PRODUCT_NOT_FOUND = "상품이 존재하지 않습니다."
    }
}
