package woowacourse.shopping.view.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.decrementQuantity
import woowacourse.shopping.domain.model.incrementQuantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
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

    private val _totalCount = MutableLiveData<Int>(1)
    val totalCount: LiveData<Int> get() = _totalCount

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
                val quantity = cartMap[product.id]?.quantity ?: DEFAULT_QUANTITY
                ProductWithQuantity(product, quantity)
            }

        val updatedProductList = loadedItems + newProductsWithQuantity
        _products.value = PagingResult(updatedProductList, hasNextPage)
        _totalCount.value = updatedProductList.sumOf { it.quantity }
        Log.d("yenny", "${totalCount.value}")
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
            val newId = cartRepository.addCartItem(product, INCREMENT_VALUE)
            updatedCartItem = CartItem(newId, product, INCREMENT_VALUE)
        } else {
            updatedCartItem = cartItem.incrementQuantity(INCREMENT_VALUE)
            cartRepository.updateCartItem(updatedCartItem)
        }
        _updatedCountInfo.value = ProductWithQuantity(updatedCartItem.product, updatedCartItem.quantity)
        _totalCount.value = _totalCount.value?.plus(INCREMENT_VALUE)
        Log.d("yenny", "${totalCount.value}")
    }

    override fun onDecreaseQuantityButtonClicked(id: Long) {
        val cartItem = cartRepository.findCartItemWithProductId(id) ?: throw NoSuchDataException()
        val updatedCartItem = cartItem.decrementQuantity(DECREMENT_VALUE)
        cartRepository.updateCartItem(updatedCartItem)
        _updatedCountInfo.value = ProductWithQuantity(updatedCartItem.product, updatedCartItem.quantity)
        _totalCount.value = _totalCount.value?.minus(INCREMENT_VALUE)
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
        const val DEFAULT_QUANTITY = 0
        const val INCREMENT_VALUE = 1
        const val DECREMENT_VALUE = 1
    }
}
