package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.cart.CartItem.PaginationButtonItem
import woowacourse.shopping.cart.CartItem.ProductItem
import woowacourse.shopping.data.mapper.toUiModel
import woowacourse.shopping.data.repository.CartProductRepository
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel(
    private val cartProductRepository: CartProductRepository,
) : ViewModel() {
    private val _cartProducts = MutableLiveData<List<CartItem>>()
    val cartProducts: LiveData<List<CartItem>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page

    private val _updatedItem = MutableLiveData<ProductUiModel>()
    val updatedItem: LiveData<ProductUiModel> = _updatedItem

    private val _updateButton = MutableLiveData<PaginationButtonItem>()
    val updateButton: LiveData<PaginationButtonItem> = _updateButton

    init {
        loadCartProducts()
    }

    fun deleteCartProduct(productId: Int) {
        cartProductRepository.deleteCartProduct(productId) {
            cartProductRepository.getAllProductsSize { updatedSize ->
                val currentPage = page.value ?: INITIAL_PAGE
                val startIndex = currentPage * PAGE_SIZE
                if (startIndex >= updatedSize && currentPage > 0) {
                    decreasePage()
                }
                loadCartProducts()
            }
        }
    }

    fun onPaginationButtonClick(buttonEvent: ButtonEvent) {
        cartProductRepository.getAllProductsSize { totalSize ->
            val currentPage = page.value ?: INITIAL_PAGE
            val lastPage = (totalSize - 1) / PAGE_SIZE

            when (buttonEvent) {
                ButtonEvent.DECREASE -> {
                    if (currentPage > 0) {
                        decreasePage()
                        loadCartProducts()
                    }
                }

                ButtonEvent.INCREASE -> {
                    if (currentPage < lastPage) {
                        increasePage()
                        loadCartProducts()
                    }
                }
            }
        }
    }

    fun updateQuantity(
        buttonEvent: ButtonEvent,
        product: ProductUiModel,
    ) {
        when (buttonEvent) {
            ButtonEvent.INCREASE -> {
                cartProductRepository.updateProductQuantity(product.id, 1) {
                    cartProductRepository.getProduct(product.id) { product ->
                        _updatedItem.postValue(product.toUiModel())
                    }
                }
            }

            ButtonEvent.DECREASE -> {
                if (product.quantity == 1) return
                cartProductRepository.updateProductQuantity(product.id, -1) {
                    cartProductRepository.getProduct(product.id) { product ->
                        _updatedItem.postValue(product.toUiModel())
                    }
                }
            }
        }
    }

    fun updateButton() {
        _updateButton.postValue(getPaginationButton())
    }

    private fun checkNextButtonEnabled(totalSize: Int) {
        val currentPage = page.value ?: INITIAL_PAGE
        val lastPage = (totalSize - 1) / PAGE_SIZE
        _isNextButtonEnabled.postValue(currentPage < lastPage)
    }

    private fun checkPrevButtonEnabled() {
        val currentPage = page.value ?: INITIAL_PAGE
        _isPrevButtonEnabled.postValue(currentPage >= 1)
    }

    private fun increasePage() {
        _page.postValue(page.value?.plus(1))
    }

    private fun decreasePage() {
        _page.postValue(page.value?.minus(1))
    }

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        cartProductRepository.getAllProductsSize { totalSize ->
            val currentPage = page.value ?: INITIAL_PAGE
            val startIndex = currentPage * pageSize
            val endIndex = minOf(startIndex + pageSize, totalSize)

            if (startIndex >= totalSize) {
                return@getAllProductsSize
            }

            cartProductRepository.getCartProductsInRange(startIndex, endIndex) { cartProducts ->
                val pagedProducts: List<ProductItem> =
                    cartProducts.map { ProductItem(it.toUiModel()) }
                val paginationButton = getPaginationButton()

                _cartProducts.postValue(pagedProducts + paginationButton)
                checkNextButtonEnabled(totalSize)
                checkPrevButtonEnabled()
            }
        }
    }

    private fun getPaginationButton(): PaginationButtonItem =
        PaginationButtonItem(
            page = (page.value ?: 0) + 1,
            isNextButtonEnabled = isNextButtonEnabled.value ?: false,
            isPrevButtonEnabled = isPrevButtonEnabled.value ?: false,
        )

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0
    }
}
