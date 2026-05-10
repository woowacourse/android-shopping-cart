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
import woowacourse.shopping.domain.product.model.Product

class ProductDetailViewModel(
    parcelProduct: ParcelProduct,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private var product: Product = parcelProduct.toProduct()
    private var name = ""
    private var imageUrl = ""
    private var price = 0
    private var quantity = 0
    private var minusEnabled = false
    private var isLatestProduct = true
    private var latestProduct: Product? = null

    init {
        viewModelScope.launch {
            addRecentProducts(product.id)

            name = product.name.value
            imageUrl = product.imageUrl.value
            price = product.price.value
            quantity = 1
            minusEnabled = false
            latestProduct = recentProductRepository.getMostRecentProduct()
            isLatestProduct = (latestProduct?.id ?: product.id) == product.id

            _uiState.update {
                ProductDetailUiState(
                    productName = name,
                    productImageUrl = imageUrl,
                    productPrice = price,
                    quantity = quantity,
                    minusEnabled = minusEnabled,
                    latestProduct = latestProduct,
                    isLastProduct = isLatestProduct,
                )
            }
        }
    }

    fun changeProduct() {
        addRecentProducts(_uiState.value.latestProduct!!.id)

        isLatestProduct = true
        minusEnabled = false
        quantity = 1
        product = _uiState.value.latestProduct!!
        name = product.name.value
        imageUrl = product.imageUrl.value
        price = product.price.value

        _uiState.update {
            it.copy(
                productName = name,
                productImageUrl = imageUrl,
                productPrice = price,
                quantity = quantity,
                minusEnabled = minusEnabled,
                isLastProduct = isLatestProduct,
            )
        }
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
            it.copy(
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
            it.copy(
                productPrice = price,
                quantity = quantity,
                minusEnabled = minusEnabled,
            )
        }
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
