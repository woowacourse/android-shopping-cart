package woowacourse.shopping.features.productList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.data.DataProvider
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository = DataProvider.productRepository,
    private val cartRepository: CartRepository = DataProvider.cartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    val totalProductCount = productRepository.getProductsSize()

    var productUiList = emptyList<ProductUiModel>()
    var isLastPage = false
    var pageCount = 0

    init {
        moreProducts()
    }

    fun moreProducts() {
        val moreProducts =
            productRepository.getPagedProducts(page = pageCount, pageSize = PAGE_SIZE).map {
                ProductUiModel(
                    id = it.id,
                    name = it.name.value,
                    price = it.price.value,
                    imageUrl = it.imageUrl.value,
                    quantity = cartRepository.getQuantity(CartItem(it, quantity = CartItemQuantity(0))),
                    isExistProductToCart = cartRepository.isCartItemExist(CartItem(it, quantity = CartItemQuantity(0))),
                )
            }
        if (moreProducts.isEmpty()) return
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

    fun loadProductUiList() {
        val totalCartItemCount = cartRepository.getTotalCartItemCount()
        _uiState.update {
            it.copy(
                totalCartItemsCount = totalCartItemCount,
                productList = productUiList.map { productUi ->
                    ProductUiModel(
                        id = productUi.id,
                        name = productUi.name,
                        price = productUi.price,
                        imageUrl = productUi.imageUrl,
                        quantity = cartRepository.getQuantity(CartItem(toProductUi(productUi), quantity = CartItemQuantity(0))),
                        isExistProductToCart = cartRepository.isCartItemExist(CartItem(toProductUi(productUi), quantity = CartItemQuantity(0))),
                    )
                }
            )
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
        cartRepository.addCartItem(CartItem(toProductUi(productUiModel), quantity = CartItemQuantity(0)), 1)
        loadProductUiList()
    }

    fun minusCartItem(productUiModel: ProductUiModel) {
        val cartItem = CartItem(toProductUi(productUiModel), quantity = CartItemQuantity(0))
        val quantity = cartRepository.getQuantity(cartItem)
        if (quantity == 1) {
            cartRepository.removeCartItem(cartItem)
        } else {
            cartRepository.minusCartItem(CartItem(toProductUi(productUiModel), quantity = CartItemQuantity(0)), 1)
        }
        loadProductUiList()
    }

    fun removeCartItem(productUiModel: ProductUiModel) {
        cartRepository.removeCartItem(CartItem(toProductUi(productUiModel), quantity = CartItemQuantity(0)))
        loadProductUiList()
    }

    fun getQuantity(productUiModel: ProductUiModel): Int {
        val productQuantity = cartRepository.getQuantity(CartItem(toProductUi(productUiModel), quantity = CartItemQuantity(0)))
        return productQuantity
    }

    fun isExistProduct(productUiModel: ProductUiModel): Boolean = productUiModel.quantity > 0

    fun isHasProductId(productId: String): Boolean = productRepository.isProductExist(productId)

    companion object {
        const val PAGE_SIZE = 20
    }
}
