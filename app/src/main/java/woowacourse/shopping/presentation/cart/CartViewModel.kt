package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.CartProduct
class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _products = MutableLiveData<List<CartProduct>>(emptyList())
    val products: LiveData<List<CartProduct>> get() = _products

    private val _currentPage = MutableLiveData(INITIAL_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _maxPage = MutableLiveData(INITIAL_PAGE)
    val maxPage: LiveData<Int> get() = _maxPage

    init {
        updateCartProducts()
        updateMaxPage()
    }

    fun updateCartProducts() {
        val page = currentPage.value ?: INITIAL_PAGE
        cartRepository.fetchCartProducts(page) { result ->
            _products.postValue(result)
            if (result.isEmpty() && page > INITIAL_PAGE) {
                decreasePage()
            }
        }
    }

    fun updateMaxPage() {
        cartRepository.fetchMaxPageCount { count ->
            _maxPage.postValue(count)
        }
    }

    fun removeCartProduct(id: Int) {
        cartRepository.removeCartProduct(id)
        updateCartProducts()
        updateMaxPage()
    }

    fun increasePage(step: Int = DEFAULT_PAGE_STEP) {
        _currentPage.postValue((currentPage.value ?: INITIAL_PAGE) + step)
        updateCartProducts()
    }

    fun decreasePage(step: Int = DEFAULT_PAGE_STEP) {
        if (currentPage.value == INITIAL_PAGE) return
        _currentPage.postValue((currentPage.value ?: INITIAL_PAGE) - step)
        updateCartProducts()
    }

    fun increaseCount(cartProduct: CartProduct) {
        val updatedList = _products.value.orEmpty().map {
            if (it.product.id == cartProduct.product.id) it.copy(count = it.count + 1) else it
        }
        _products.postValue(updatedList)
        cartRepository.upsertCartProduct(cartProduct.product, 1)
    }

    fun decreaseCount(cartProduct: CartProduct) {
        if (cartProduct.count == 1) {
            val updatedList = _products.value.orEmpty()
                .filter { it.product.id != cartProduct.product.id }
            _products.postValue(updatedList)
            cartRepository.removeCartProduct(cartProduct.product.id)
        } else {
            val updatedList = _products.value.orEmpty().map {
                if (it.product.id == cartProduct.product.id) it.copy(count = it.count - 1) else it
            }
            _products.postValue(updatedList)
            cartRepository.upsertCartProduct(cartProduct.product, -1)
        }
    }

    companion object {
        const val INITIAL_PAGE = 1
        const val DEFAULT_PAGE_STEP = 1
    }
}