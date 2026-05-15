package woowacourse.shopping.features.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.NetworkMonitor
import woowacourse.shopping.domain.RecentProductRepository
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.repository.ProductRepository
import woowacourse.shopping.features.productDetail.ParcelProduct
import woowacourse.shopping.features.productDetail.toParcelProduct

class ProductListViewModel(
    networkMonitor: NetworkMonitor,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    val isOnline: StateFlow<Boolean> =
        networkMonitor.isConnected.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            true,
        )
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProductUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var totalProductCount = 0
    private var productUiList = emptyList<ProductUiModel>()
    private var isLastPage = false
    private var pageCount = 0
    private var totalCartItemCount = 0

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
            val cart = cartRepository.getCart()
            val moreProducts =
                productRepository
                    .getPagedProducts(page = pageCount, pageSize = PAGE_SIZE)
                    .map { product ->
                        ProductUiModel(
                            id = product.id,
                            name = product.name.value,
                            price = product.price.value,
                            imageUrl = product.imageUrl.value,
                            quantity = cart.getQuantity(CartItem(product, quantity = CartItemQuantity(1))),
                            isExistProductToCart = cart.searchCartItem(CartItem(product, quantity = CartItemQuantity(1))),
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
            val cart = cartRepository.getCart()

            val updatedList =
                productUiList.map { productUiModel ->
                    val cartItem = CartItem(productUiModel.toProduct(), quantity = CartItemQuantity(0))
                    val curQuantity = cart.getQuantity(cartItem)

                    if (curQuantity > 0) {
                        productUiModel.copy(
                            quantity = curQuantity,
                            isExistProductToCart = true,
                        )
                    } else {
                        productUiModel.copy(
                            quantity = 0,
                            isExistProductToCart = false,
                        )
                    }
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
                cartItem =
                    CartItem(
                        product = productUiModel.toProduct(),
                        quantity = CartItemQuantity(1),
                    ),
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
                    cartItem =
                        CartItem(
                            product = productUiModel.toProduct(),
                            quantity = CartItemQuantity(1),
                        ),
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
                    quantity = CartItemQuantity(productUiModel.quantity),
                ),
            )
            loadProductUiList()
        }
    }

    fun isExistProduct(productUiModel: ProductUiModel): Boolean = productUiModel.quantity > 0

    fun isHasProductId(productUi: ProductUiModel) {
        viewModelScope.launch {
            val exist = productRepository.isProductExist(productUi.id)
            if (exist) {
                _uiEvent.send(ProductUiEvent.NextPage(productUi.toProduct().toParcelProduct()))
            } else {
                _uiEvent.send(ProductUiEvent.ShowToast("상품이 존재하지 않습니다."))
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

sealed interface ProductUiEvent {
    data class ShowToast(val message: String) : ProductUiEvent

    data class NextPage(val parcelProduct: ParcelProduct) : ProductUiEvent
}

class ProductListViewModelFactory(
    private val networkMonitor: NetworkMonitor,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(
                networkMonitor,
                productRepository,
                cartRepository,
                recentProductRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
