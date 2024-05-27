package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.joinProductAndCart
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentRepository
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event
import java.time.LocalDateTime
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentRepository: RecentRepository,
) : ViewModel(), DetailHandler {
    private val _shoppingProduct =
        MutableLiveData<UiState<ProductListItem.ShoppingProductItem>>(UiState.None)
    val shoppingProduct: LiveData<UiState<ProductListItem.ShoppingProductItem>> get() = _shoppingProduct

    private val _lastProduct = MutableLiveData<UiState<RecentProductItem>>()
    val lastProduct: LiveData<UiState<RecentProductItem>> get() = _lastProduct

    private lateinit var shoppingProductItem: ProductListItem.ShoppingProductItem

    private val _error = MutableLiveData<Event<DetailError>>()
    val error: LiveData<Event<DetailError>> get() = _error

    private val _moveEvent = MutableLiveData<Event<FromDetailToScreen>>()
    val moveEvent: LiveData<Event<FromDetailToScreen>> get() = _moveEvent

    fun fetchInitialData(productId: Long) {
        fetchProductById(productId)
    }

    private fun fetchProductById(productId: Long) {
        productRepository.loadById(productId).onSuccess {
            loadCartProductById(it)
            addRecentProduct(it)
        }.onFailure {
            _error.value =
                Event(DetailError.ProductItemsNotFound)
        }
    }

    fun loadLastProduct() {
        recentRepository.loadMostRecent().onSuccess {
            it?.let {
                _lastProduct.value = UiState.Success(it)
            }
        }.onFailure {
            _error.value =
                Event(DetailError.RecentItemNotFound)
        }
    }

    private fun addRecentProduct(product: Product) {
        thread {
            recentRepository.add(
                RecentProductItem(
                    productId = product.id,
                    name = product.name,
                    imgUrl = product.imgUrl,
                    dateTime = LocalDateTime.now(),
                ),
            )
        }.join()
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

    private fun updateCartItemQuantity(quantityDelta: Int) {
        val originalQuantity = shoppingProductItem.quantity
        val newShoppingProductItem =
            shoppingProductItem.copy(quantity = (originalQuantity + quantityDelta).coerceAtLeast(0))
        shoppingProductItem = newShoppingProductItem
        _shoppingProduct.value = UiState.Success(shoppingProductItem)
    }

    fun saveCartItem() {
        cartRepository.setQuantity(shoppingProductItem.toProduct(), shoppingProductItem.quantity)
            .onSuccess {
                _moveEvent.value =
                    Event(
                        FromDetailToScreen.Shopping(
                            shoppingProductItem.id,
                            shoppingProductItem.quantity,
                        ),
                    )
            }.onFailure {
                _error.postValue(Event(DetailError.CartItemNotFound))
            }
    }

    override fun onAddCartClick() {
        saveCartItem()
    }

    override fun onLastProductClick(product: RecentProductItem) {
        _moveEvent.value = Event(FromDetailToScreen.ProductDetail(product.productId))
    }

    override fun onDecreaseQuantity(item: ProductListItem.ShoppingProductItem?) {
        item?.let {
            updateCartItemQuantity(
                -1,
            )
        }
    }

    override fun onIncreaseQuantity(item: ProductListItem.ShoppingProductItem?) {
        item?.let {
            updateCartItemQuantity(
                1,
            )
        }
    }
}
