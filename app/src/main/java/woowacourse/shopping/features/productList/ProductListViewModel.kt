package woowacourse.shopping.features.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.RecentProductRepository
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private var totalProductCount = 0
    private var productUiList = emptyList<ProductUiModel>()
    private var isLastPage = false
    private var pageCount = 0
    private var totalCartItemCount = 0
    var isHasProductId = false

    init {
        moreProducts()
    }

    fun loadRecentProducts() {
        viewModelScope.launch {
            totalProductCount = productRepository.getProductsSize()
            val recentProductUiList =
                recentProductRepository.getAllRecentProducts().map { product ->
                    ProductUiModel(
                        id = product.id,
                        name = product.name.value,
                        imageUrl = product.imageUrl.value,
                        price = product.price.value,
                        quantity = 0,
                        isExistProductToCart = false,
                    )
                }
            _uiState.update {
                it.copy(
                    recentProductList = recentProductUiList,
                )
            }
        }
    }

    fun moreProducts() {
        viewModelScope.launch {
            val moreProducts =
                productRepository.getPagedProducts(page = pageCount, pageSize = PAGE_SIZE)
                    .map { product ->
                        ProductUiModel(
                            id = product.id,
                            name = product.name.value,
                            price = product.price.value,
                            imageUrl = product.imageUrl.value,
                            quantity =
                                cartRepository.getQuantity(
                                    CartItem(
                                        product = product,
                                        quantity = CartItemQuantity(1),
                                    ),
                                ),
                            isExistProductToCart =
                                cartRepository.isCartItemExist(
                                    CartItem(
                                        product = product,
                                        quantity = CartItemQuantity(1),
                                    ),
                                ),
                        )
                    }
            productUiList += moreProducts
            isLastPage = productUiList.size >= totalProductCount
            pageCount += 1
            _uiState.update {
                it.copy(
                    productList = productUiList,
                    isLastPage = isLastPage,
                )
            }
        }
    }

    fun loadProductUiList() {
        viewModelScope.launch {
            totalCartItemCount = cartRepository.getTotalCartItemCount()

            val updatedList =
                productUiList.map { productUiModel ->
                    productUiModel.copy(
                        quantity =
                            cartRepository.getQuantity(
                                CartItem(
                                    product = productUiModel.toProduct(),
                                    quantity = CartItemQuantity(1),
                                ),
                            ),
                        isExistProductToCart =
                            cartRepository.isCartItemExist(
                                CartItem(
                                    product = productUiModel.toProduct(),
                                    quantity = CartItemQuantity(1),
                                ),
                            ),
                    )
                }

            productUiList = updatedList

            _uiState.update {
                it.copy(
                    totalCartItemsCount = totalCartItemCount,
                    productList = productUiList,
                )
            }
        }
    }

    fun addCartItem(productUiModel: ProductUiModel) {
        viewModelScope.launch {
            cartRepository.addCartItem(
                cartItem = CartItem(
                    product = productUiModel.toProduct(),
                    quantity = CartItemQuantity(1),
                ),
                targetQuantity = 1
            )
            loadProductUiList()
        }
    }

    fun minusCartItem(productUiModel: ProductUiModel) {
        viewModelScope.launch {
            val cartItem = CartItem(productUiModel.toProduct(), quantity = CartItemQuantity(0))
            val quantity = cartRepository.getQuantity(cartItem)
            if (quantity == 1) {
                cartRepository.removeCartItem(cartItem)
            } else {
                cartRepository.minusCartItem(
                    cartItem = CartItem(
                        product = productUiModel.toProduct(),
                        quantity = CartItemQuantity(1),
                    ),
                    targetQuantity = 1,
                )
            }
            loadProductUiList()
        }
    }

    fun removeCartItem(productUiModel: ProductUiModel) {
        viewModelScope.launch {
            cartRepository.removeCartItem(
                CartItem(
                    product = productUiModel.toProduct(),
                    quantity = CartItemQuantity(1),
                ),
            )
            loadProductUiList()
        }
    }

    fun isExistProduct(productUiModel: ProductUiModel): Boolean = productUiModel.quantity > 0

    fun isHasProductId(productId: String) {
        viewModelScope.launch {
            isHasProductId = productRepository.isProductExist(productId)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

class ProductListViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(
                productRepository,
                cartRepository,
                recentProductRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
