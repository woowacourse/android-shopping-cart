package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct
import woowacourse.shopping.view.common.MutableSingleLiveData
import woowacourse.shopping.view.common.SingleLiveData
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.PaginationItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem.ProductItem

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository.get(),
) : ViewModel() {
    private val _shoppingCart: MutableLiveData<List<ShoppingCartItem>> = MutableLiveData()
    val shoppingCart: LiveData<List<ShoppingCartItem>> get() = _shoppingCart

    private val _updatedProducts: MutableLiveData<List<Product>> =
        MutableLiveData()
    val updatedProducts: LiveData<List<Product>> get() = _updatedProducts

    private val _event: MutableSingleLiveData<ShoppingCartEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ShoppingCartEvent> get() = _event

    private var page: Int = MINIMUM_PAGE
    private var hasPreviousPage: Boolean = false
    private var hasNextPage: Boolean = false

    fun updateShoppingCart() {
        val offset = (page - 1) * COUNT_PER_PAGE
        val limit = COUNT_PER_PAGE + 1
        shoppingCartRepository.load(offset, limit) { result ->
            result
                .onSuccess { shoppingCartProducts: List<ShoppingCartProduct> ->
                    if (isEmptyPage(shoppingCartProducts)) return@load

                    updatePaginationState(shoppingCartProducts)

                    val items = createShoppingCartItems(shoppingCartProducts)
                    _shoppingCart.postValue(items)
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
                }
        }
    }

    private fun isEmptyPage(products: List<ShoppingCartProduct>): Boolean =
        if (products.isEmpty() && page != MINIMUM_PAGE) {
            minusPage()
            true
        } else {
            false
        }

    private fun updatePaginationState(products: List<ShoppingCartProduct>) {
        hasNextPage = products.size > COUNT_PER_PAGE
        hasPreviousPage = page > MINIMUM_PAGE
    }

    private fun createShoppingCartItems(products: List<ShoppingCartProduct>): List<ShoppingCartItem> {
        val visibleProducts = products.take(COUNT_PER_PAGE)
        val paginationItem =
            PaginationItem(
                page = page,
                nextEnabled = hasNextPage,
                previousEnabled = hasPreviousPage,
            )

        return visibleProducts.map(::ProductItem) + paginationItem
    }

    fun removeShoppingCartProduct(product: Product) {
        shoppingCartRepository.remove(product) { result ->
            result
                .onSuccess {
                    updateShoppingCart()
                    val currentUpdatedProducts =
                        updatedProducts.value?.toMutableList() ?: mutableListOf()
                    if (currentUpdatedProducts.contains(product)) return@remove
                    currentUpdatedProducts.add(product)
                    _updatedProducts.postValue(currentUpdatedProducts)
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.REMOVE_SHOPPING_CART_PRODUCT_FAILURE)
                }
        }
    }

    fun decreaseQuantity(product: Product) {
        shoppingCartRepository.decreaseQuantity(product) { result ->
            result
                .onSuccess {
                    updateShoppingCart()
                    val currentUpdatedProducts =
                        updatedProducts.value?.toMutableList() ?: mutableListOf()
                    if (currentUpdatedProducts.contains(product)) return@decreaseQuantity
                    currentUpdatedProducts.add(product)
                    _updatedProducts.postValue(currentUpdatedProducts)
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.DECREASE_SHOPPING_CART_PRODUCT_FAILURE)
                }
        }
    }

    fun addQuantity(product: Product) {
        shoppingCartRepository.add(product, 1) { result ->
            result
                .onSuccess {
                    updateShoppingCart()
                    val currentUpdatedProducts =
                        updatedProducts.value?.toMutableList() ?: mutableListOf()
                    if (currentUpdatedProducts.contains(product)) return@add
                    currentUpdatedProducts.add(product)
                    _updatedProducts.postValue(currentUpdatedProducts)
                }.onFailure {
                    _event.postValue(ShoppingCartEvent.ADD_SHOPPING_CART_PRODUCT_FAILURE)
                }
        }
    }

    fun plusPage() {
        page++
        updateShoppingCart()
    }

    fun minusPage() {
        page = page.minus(1).coerceAtLeast(MINIMUM_PAGE)
        updateShoppingCart()
    }

    companion object {
        private const val MINIMUM_PAGE = 1
        private const val COUNT_PER_PAGE: Int = 5
    }
}
