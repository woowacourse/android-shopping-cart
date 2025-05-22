package woowacourse.shopping.presentation.viewmodel.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.model.CartProduct

class CartViewModel(
    private val cartDummyRepository: CartRepository = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _products: MutableLiveData<List<CartProduct>> =
        MutableLiveData(emptyList<CartProduct>())
    val products: LiveData<List<CartProduct>> get() = _products

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _maxPage: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_PAGE)
    val maxPage: LiveData<Int> get() = _maxPage

    init {
        updateCartProducts()
    }

    fun updateCartProducts() {
        _products.value =
            cartDummyRepository.fetchCartProducts(currentPage.value ?: INITIAL_PAGE).map { it }
        if (products.value.isNullOrEmpty()) decreasePage()
        updateMaxPage()
    }

    fun updateMaxPage() {
        _maxPage.value = cartDummyRepository.fetchMaxPageCount()
    }

    fun removeCartProduct(id: Int) {
        cartDummyRepository.removeCartProduct(id)
        updateCartProducts()
    }

    fun increasePage(step: Int = DEFAULT_PAGE_STEP) {
        _currentPage.value = currentPage.value?.plus(step)

        updateCartProducts()
    }

    fun decreasePage(step: Int = DEFAULT_PAGE_STEP) {
        if (currentPage.value == INITIAL_PAGE) return
        _currentPage.value = currentPage.value?.minus(step)
        updateCartProducts()
    }

    fun increaseCount(cartProduct: CartProduct) {
        val products: List<CartProduct> =
            _products.value
                .orEmpty()
                .toMutableList()
                .map { if (it.product.id == cartProduct.product.id) it.apply { it.count += 1 } else it }
        _products.value = products
        cartDummyRepository.upsertCartProduct(cartProduct.product, 1)
    }

    fun decreaseCount(cartProduct: CartProduct) {
        if (cartProduct.count == 1) {
            val products: List<CartProduct> =
                products.value
                    .orEmpty()
                    .toMutableList()
                    .filter { it.product.id != cartProduct.product.id }
            _products.value = products
            cartDummyRepository.removeCartProduct(cartProduct.product.id)
            updateCartProducts()
        } else {
            val products: List<CartProduct> =
                _products.value
                    .orEmpty()
                    .toMutableList()
                    .map { if (it.product.id == cartProduct.product.id) it.apply { it.count -= 1 } else it }
            _products.value = products
            cartDummyRepository.upsertCartProduct(cartProduct.product, -1)
        }
    }

    companion object {
        const val INITIAL_PAGE: Int = 1
        const val DEFAULT_PAGE_STEP: Int = 1
    }
}
