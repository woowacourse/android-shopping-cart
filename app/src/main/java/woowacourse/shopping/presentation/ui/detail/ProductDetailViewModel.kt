package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.joinProductAndCart
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _shoppingProduct =
        MutableLiveData<UiState<ProductListItem.ShoppingProductItem>>(UiState.None)
    val shoppingProduct: LiveData<UiState<ProductListItem.ShoppingProductItem>> get() = _shoppingProduct

    lateinit var shoppingProductItem: ProductListItem.ShoppingProductItem

    private val _error = MutableLiveData<Event<DetailError>>()
    val error: LiveData<Event<DetailError>> get() = _error

    private val _addCartEvent = MutableLiveData<Event<Int>>()
    val addCartEvent: LiveData<Event<Int>>
        get() = _addCartEvent

    fun loadProductById(productId: Long) {
        productRepository.loadById(productId).onSuccess {
            loadCartProductById(it)
        }.onFailure {
            _error.value =
                Event(DetailError.ProductItemsNotFound)
        }
    }

    private fun loadCartProductById(product: Product) {
        cartRepository.find(product).onSuccess {
            shoppingProductItem = joinProductAndCart(product, it)
            _shoppingProduct.value = UiState.Success(shoppingProductItem)
        }.onFailure {
            shoppingProductItem =
                ProductListItem.ShoppingProductItem(
                    id = product.id,
                    imgUrl = product.imgUrl,
                    name = product.name,
                    price = product.price,
                    quantity = 0,
                )
            _shoppingProduct.value = UiState.Success(shoppingProductItem)
        }
    }

    fun updateCartItemQuantity(quantityDelta: Int) {
        val originalQuantity = shoppingProductItem.quantity
        val newShoppingProductItem =
            shoppingProductItem.copy(quantity = originalQuantity + quantityDelta)
        shoppingProductItem = newShoppingProductItem
        _shoppingProduct.value = UiState.Success(shoppingProductItem)
    }

    fun saveCartItem() {
        cartRepository.setQuantity(shoppingProductItem.toProduct(), shoppingProductItem.quantity)
            .onSuccess {
                _addCartEvent.value = Event(shoppingProductItem.quantity)
            }
    }
}
