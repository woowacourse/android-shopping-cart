package woowacourse.shopping.view.products

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
import woowacourse.shopping.view.ProductUpdate

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ProductListActionHandler, CountActionHandler {
    private val _products: MutableLiveData<PagingResult> =
        MutableLiveData(PagingResult(emptyList(), false))
    val products: LiveData<PagingResult> get() = _products

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>> get() = _navigateToCart

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>> get() = _navigateToDetail

    private val _updateProductCount = MutableLiveData<Event<ProductUpdate>>()

    val updateProductCount: LiveData<Event<ProductUpdate>> = _updateProductCount

    private val _totalCount = MutableLiveData<Int>(0)
    val totalCount: LiveData<Int> get() = _totalCount

    init {
        loadPagingProductData()
        updateTotalCount()
    }

    private fun loadPagingProductData() {
        val loadedItems = products.value?.items ?: emptyList()
        val pagingProducts =
            productRepository.loadPagingProducts(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)
        val hasNextPage =
            productRepository.hasNextProductPage(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)

        val cartItems = cartRepository.loadAllCartItems()
        val cartMap = cartItems.associateBy { it.product.id }

        val newProductsWithQuantity =
            pagingProducts.map { product ->
                val quantity = cartMap[product.id]?.quantity ?: DEFAULT_QUANTITY
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
            val newId = cartRepository.addCartItem(product, INCREMENT_VALUE)
            updatedCartItem = CartItem(newId, product, INCREMENT_VALUE)
        } else {
            updatedCartItem = cartItem.incrementQuantity(INCREMENT_VALUE)
            cartRepository.updateCartItem(updatedCartItem)
        }
        _updateProductCount.value = Event(ProductUpdate(updatedCartItem.product.id, updatedCartItem.quantity))
        _totalCount.value = _totalCount.value?.plus(INCREMENT_VALUE)
    }

    override fun onDecreaseQuantityButtonClicked(id: Long) {
        val cartItem = cartRepository.findCartItemWithProductId(id) ?: throw NoSuchDataException()
        val updatedCartItem = cartItem.decrementQuantity(DECREMENT_VALUE)

        if (updatedCartItem.quantity == 0) {
            cartRepository.deleteCartItem(updatedCartItem.id)
        } else {
            cartRepository.updateCartItem(updatedCartItem)
        }
        _updateProductCount.value =
            Event(ProductUpdate(updatedCartItem.product.id, updatedCartItem.quantity))
        _totalCount.value = _totalCount.value?.minus(INCREMENT_VALUE)
    }

    fun updateTotalCount() {
        _totalCount.value = cartRepository.getTotalNumberOfCartItems()
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
        const val DEFAULT_QUANTITY = 0
        const val INCREMENT_VALUE = 1
        const val DECREMENT_VALUE = 1
    }
}
