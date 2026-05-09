package woowacourse.shopping.features.productDetail

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
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class ProductDetailViewModel(
    parcelProduct: ParcelProduct,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    val product: Product = toProduct(parcelProduct)
    var price = 0
    var quantity = 0
    var minusEnabled = false

    init {
        price = product.price.value
        quantity = 1
        minusEnabled = false
        _uiState.update {
            ProductDetailUiState(
                productPrice = price,
                quantity = quantity,
                minusEnabled = minusEnabled,
            )
        }
        addRecentProducts(product.id)
    }

    fun addRecentProducts(productId: String) {
        viewModelScope.launch {
            recentProductRepository.addRecentProduct(productId)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val cartItem = CartItem(product = product, quantity = CartItemQuantity(1))
            cartRepository.addCartItem(cartItem, quantity)
        }
    }

    fun increaseCartItem() {
        price += product.price.value
        quantity += 1
        minusEnabled = quantity > 1
        _uiState.update {
            ProductDetailUiState(
                productPrice = price,
                quantity = quantity,
                minusEnabled = minusEnabled,
            )
        }
    }

    fun decreaseCartItem() {
        price -= product.price.value
        quantity -= 1
        minusEnabled = quantity > 1
        _uiState.update {
            ProductDetailUiState(
                productPrice = price,
                quantity = quantity,
                minusEnabled = minusEnabled,
            )
        }
    }

    companion object {
        fun from(product: Product): ParcelProduct =
            ParcelProduct(
                id = product.id,
                name = product.name.value,
                price = product.price.value,
                imageUrl = product.imageUrl.value,
            )

        fun toProduct(parcelProduct: ParcelProduct): Product =
            Product(
                id = parcelProduct.id,
                name = ProductName(parcelProduct.name),
                price = Price(parcelProduct.price),
                imageUrl = ImageUrl(parcelProduct.imageUrl),
            )
    }
}

class ProductDetailViewModelFactory(
    private val product: ParcelProduct,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(product, cartRepository, recentProductRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
