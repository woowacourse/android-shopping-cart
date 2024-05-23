package woowacourse.shopping.view.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.incrementQuantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.Event

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ProductListActionHandler, CountActionHandler {
    private val _products: MutableLiveData<PagingResult> = MutableLiveData(PagingResult(emptyList(), false))
    val products: LiveData<PagingResult> get() = _products

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>> get() = _navigateToCart

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>> get() = _navigateToDetail

    private val _updatedCountInfo: MutableLiveData<ProductWithQuantity> = MutableLiveData()
    val updatedCountInfo: LiveData<ProductWithQuantity> get() = _updatedCountInfo

    init {
        loadPagingProductData()
    }

    private fun loadPagingProductData() {
        val loadedItems = products.value?.items ?: emptyList()
        val pagingProducts = productRepository.loadPagingProducts(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)
        val hasNextPage = productRepository.hasNextProductPage(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)

        val cartItems = cartRepository.loadAllCartItems()
        val cartMap = cartItems.associateBy { it.product.id }

        val newProductsWithQuantity =
            pagingProducts.map { product ->
                val quantity = cartMap[product.id]?.quantity ?: 0
                ProductWithQuantity(product, quantity)
            }

        val updatedProductList = loadedItems + newProductsWithQuantity
        _products.value = PagingResult(updatedProductList, hasNextPage)
    }

    override fun onProductItemClicked(productId: Long) {
        _navigateToDetail.value = Event(productId)
    }

    override fun onShoppingCartButtonClicked() {
        _navigateToCart.value = Event(true)
    }

    override fun onMoreButtonClicked() {
        loadPagingProductData()
    }

    override fun onIncreaseQuantityButtonClicked(id: Long) {
        val cartItem = cartRepository.findCartItemWithProductId(id)
        val updatedCartItem: CartItem
        if (cartItem == null) {
            val product = productRepository.getProduct(id)
            val newId = cartRepository.addCartItem(product, 1)
            updatedCartItem = CartItem(newId, product, 1)
        } else {
            updatedCartItem = cartItem.incrementQuantity(1)
            cartRepository.updateCartItem(updatedCartItem)
        }
        _updatedCountInfo.value = ProductWithQuantity(updatedCartItem.product, updatedCartItem.quantity)
    }

    override fun onDecreaseQuantityButtonClicked(id: Long) {
        Log.d("yenny", "id: $id -")
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
    }
}
