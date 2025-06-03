package woowacourse.shopping.ui.fashionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel(), QuantityClickListener {

    private val _productsUiState = MutableLiveData<List<ProductListViewType>>(emptyList())
    val productsUiState: LiveData<List<ProductListViewType>> = _productsUiState

    private var pageNumber = 0

    private var cartItems: List<CartItem> = emptyList()

    init {
        loadProducts()
    }

    fun loadProducts() {
        val products = productRepository.getProductPagedItems(pageNumber++, 20)

        historyRepository.getRecentProducts(10) { recentIds ->
            val recentProductsUiState: ProductListViewType? = if (recentIds.isNotEmpty()) {
                val recentProducts = recentIds.map { id ->
                    productRepository.fetchById(id)
                }
                ProductListViewType.RecentProducts(recentProducts)
            } else {
                null
            }

            cartRepository.getAll { carts ->
                cartItems = carts
                val cartMap = cartItems.associateBy { it.id }

                val newUiStates = products.map { product ->
                    ProductListViewType.FashionProductItem(
                        product = product,
                        cartItem = cartMap[product.id],
                        isButtonVisible = cartMap[product.id] == null,
                    )
                }

                val current = _productsUiState.value?.toMutableList() ?: mutableListOf()

                recentProductsUiState?.let { current.add(it) }

                current.addAll(newUiStates)

                _productsUiState.postValue(current)
            }
        }
    }



    fun onFloatingButtonClick(product: Product) {
    updateUi(product.id) { state ->
        state.copy(
            isButtonVisible = false,
            cartItem = CartItem(product.id, product, 1)
        )
    }

    val cartItem = CartItem(product.id, product, 1)
    cartRepository.upsert(cartItem) { }
}

fun onClickLoadMore() {
    loadProducts()
}

private fun updateUi(
    productId: Long,
    transform: (ProductListViewType.FashionProductItem) -> ProductListViewType.FashionProductItem
) {
    val current = _productsUiState.value?.toMutableList() ?: return
    val index =
        current.indexOfFirst { it is ProductListViewType.FashionProductItem && it.product.id == productId }
    if (index == -1) return

    val updated = transform(current[index] as ProductListViewType.FashionProductItem)
    current[index] = updated
    _productsUiState.postValue(current)
}

override fun onClickIncrease(cartItem: CartItem) {
    val newItem = cartItem.copy(quantity = cartItem.quantity + 1)
    cartRepository.upsert(newItem) {
        updateUi(cartItem.id) { state -> state.copy(cartItem = newItem) }
    }
}

override fun onClickDecrease(cartItem: CartItem) {
    if (cartItem.quantity == 1) {
        cartRepository.removeById(cartItem.id) {
            updateUi(cartItem.id) { state ->
                state.copy(cartItem = null, isButtonVisible = true)
            }
        }
        return
    }

    val newItem = cartItem.copy(quantity = cartItem.quantity - 1)
    cartRepository.upsert(newItem) {
        updateUi(cartItem.id) { state -> state.copy(cartItem = newItem) }
    }
}
}

