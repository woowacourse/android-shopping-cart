package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.ui.products.ProductUiModel

class CartViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _productUiModels = MutableLiveData<MutableList<ProductUiModel>>()
    val productUiModels: LiveData<List<ProductUiModel>> = _productUiModels.map { it.toList() }

    private val _page = MutableLiveData<Int>(INITIALIZE_PAGE)
    val page: LiveData<Int> get() = _page

    private val totalCartCount = MutableLiveData<Int>(INITIALIZE_CART_SIZE)

    val hasPage: LiveData<Boolean> = totalCartCount.map { changePageVisibility(it) }
    val hasPreviousPage: LiveData<Boolean> = _page.map { changePreviousPageVisibility(it) }
    val hasNextPage: LiveData<Boolean> = _page.map { changeNextPageVisibility(it) }
    val isEmptyCart: LiveData<Boolean> = _productUiModels.map { it.isEmpty() }

    private var maxPage: Int = INITIALIZE_PAGE

    init {
        loadCart()
    }

    private fun loadCart() {
        val page = _page.value ?: INITIALIZE_PAGE
        val cart = cartRepository.findRange(page, PAGE_SIZE)
        _productUiModels.value = cart.toProductUiModels().toMutableList()
        loadTotalCartCount()
    }

    private fun List<CartItem>.toProductUiModels(): List<ProductUiModel> {
        return map {
            val product = productRepository.find(it.productId)
            ProductUiModel.from(product, it.quantity)
        }
    }

    private fun loadTotalCartCount() {
        val totalCartCount = cartRepository.totalProductCount()
        this.totalCartCount.value = totalCartCount
        maxPage = (totalCartCount - 1) / PAGE_SIZE
    }

    fun deleteCartItem(productId: Long) {
        cartRepository.deleteCartItem(productId)
        if (isEmptyLastPage()) {
            movePreviousPage()
            return
        }
        updateDeletedCart(productId)
    }

    private fun updateDeletedCart(productId: Long) {
        _productUiModels.value =
            _productUiModels.value?.apply {
                val productUiModel = find { productId == it.productId }
                remove(productUiModel)
            }
        totalCartCount.value = totalCartCount.value?.minus(1)
    }

    private fun isEmptyLastPage(): Boolean {
        val page = _page.value ?: INITIALIZE_PAGE
        val totalCartCount = totalCartCount.value ?: INITIALIZE_CART_SIZE
        return page > 0 && totalCartCount % PAGE_SIZE == 1
    }

    private fun changePageVisibility(totalCartCount: Int): Boolean = totalCartCount > PAGE_SIZE

    private fun changePreviousPageVisibility(page: Int): Boolean = page > INITIALIZE_PAGE

    private fun changeNextPageVisibility(page: Int): Boolean = page < maxPage

    fun moveNextPage() {
        _page.value = _page.value?.plus(1)
        loadCart()
    }

    fun movePreviousPage() {
        _page.value = _page.value?.minus(1)
        loadCart()
    }

    fun increaseQuantity(productId: Long) {
        cartRepository.increaseQuantity(productId)
        val index = _productUiModels.value?.indexOfFirst { it.productId == productId } ?: return
        _productUiModels.value =
            _productUiModels.value?.apply {
                var changedQuantity = this[index].quantity
                this[index] = this[index].copy(quantity = ++changedQuantity)
            }
    }

    fun decreaseQuantity(productId: Long) {
        val decreaseQuantityPosition =
            _productUiModels.value?.indexOfFirst { it.productId == productId } ?: return
        cartRepository.decreaseQuantity(productId)
        runCatching {
            cartRepository.find(productId)
        }.onSuccess {
            _productUiModels.value =
                _productUiModels.value?.apply {
                    val changedProductUiModel = this[decreaseQuantityPosition]
                    var changedQuantity = changedProductUiModel.quantity
                    this[decreaseQuantityPosition] = changedProductUiModel.copy(quantity = --changedQuantity)
                }
        }.onFailure {
            _productUiModels.value =
                _productUiModels.value?.also {
                    it.removeAt(decreaseQuantityPosition)
                }
        }
    }

    companion object {
        private const val INITIALIZE_CART_SIZE = 0
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 5
    }
}
