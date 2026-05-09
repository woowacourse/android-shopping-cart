package woowacourse.shopping.features.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    val totalProductCount = productRepository.getProductsSize()

    var productUiList = emptyList<ProductUiModel>()
    var isLastPage = false
    var pageCount = 0
    var totalCartItemCount = 0

    init {
        moreProducts()
    }

    fun moreProducts() {
        viewModelScope.launch {
            val moreProducts =
                productRepository.getPagedProducts(page = pageCount, pageSize = PAGE_SIZE).map {
                    ProductUiModel(
                        id = it.id,
                        name = it.name.value,
                        price = it.price.value,
                        imageUrl = it.imageUrl.value,
                        quantity =
                            cartRepository.getQuantity(
                                CartItem(
                                    it,
                                    quantity = CartItemQuantity(1),
                                ),
                            ),
                        isExistProductToCart =
                            cartRepository.isCartItemExist(
                                CartItem(
                                    it,
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
                productUiList.map { productUi ->
                    productUi.copy(
                        quantity =
                            cartRepository.getQuantity(
                                CartItem(
                                    toProductUi(productUi),
                                    quantity = CartItemQuantity(1),
                                ),
                            ),
                        isExistProductToCart =
                            cartRepository.isCartItemExist(
                                CartItem(
                                    toProductUi(productUi),
                                    quantity =
                                        CartItemQuantity(1),
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

    fun toProductUi(productUiModel: ProductUiModel): Product =
        Product(
            id = productUiModel.id,
            name = ProductName(productUiModel.name),
            price = Price(productUiModel.price),
            imageUrl = ImageUrl(productUiModel.imageUrl),
        )

    fun addCartItem(productUiModel: ProductUiModel) {
        viewModelScope.launch {
            cartRepository.addCartItem(
                CartItem(
                    toProductUi(productUiModel),
                    quantity = CartItemQuantity(1),
                ),
                1,
            )
            loadProductUiList()
        }
    }

    fun minusCartItem(productUiModel: ProductUiModel) {
        viewModelScope.launch {
            val cartItem = CartItem(toProductUi(productUiModel), quantity = CartItemQuantity(0))
            val quantity = cartRepository.getQuantity(cartItem)
            if (quantity == 1) {
                cartRepository.removeCartItem(cartItem)
            } else {
                cartRepository.minusCartItem(
                    CartItem(
                        toProductUi(productUiModel),
                        quantity = CartItemQuantity(1),
                    ),
                    1,
                )
            }
            loadProductUiList()
        }
    }

    fun removeCartItem(productUiModel: ProductUiModel) {
        viewModelScope.launch {
            cartRepository.removeCartItem(
                CartItem(
                    toProductUi(productUiModel),
                    quantity = CartItemQuantity(1),
                ),
            )
            loadProductUiList()
        }
    }

    fun isExistProduct(productUiModel: ProductUiModel): Boolean = productUiModel.quantity > 0

    fun isHasProductId(productId: String): Boolean = productRepository.isProductExist(productId)

    companion object {
        const val PAGE_SIZE = 20
    }
}

class ProductListViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(productRepository, cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
