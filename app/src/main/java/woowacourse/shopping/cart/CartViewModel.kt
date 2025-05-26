package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.cart.CartItem.ProductItem
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.mapper.toUiModel
import woowacourse.shopping.data.repository.CartProductRepository
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel(
    private val cartProductRepository: CartProductRepository,
) : ViewModel() {
    private val products = mutableListOf<ProductUiModel>()

    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page

    private val _updatedItem = MutableLiveData<ProductUiModel>()
    val updatedItem: LiveData<ProductUiModel> = _updatedItem

    init {
        loadCartProducts()
    }

    fun deleteCartProduct(cartProduct: ProductItem) {
        products.remove(cartProduct.productItem)
        cartProductRepository.deleteCartProduct(cartProduct.productItem.toEntity())

        cartProductRepository.getAllProductsSize { updatedSize ->
            val currentPage = page.value ?: INITIAL_PAGE
            val startIndex = currentPage * PAGE_SIZE
            if (startIndex >= updatedSize && currentPage > 0) {
                decreasePage()
            }
            loadCartProducts()
        }
    }

    fun onPaginationButtonClick(buttonDirection: Int) {
        cartProductRepository.getAllProductsSize { totalSize ->
            val currentPage = page.value ?: INITIAL_PAGE
            val lastPage = (totalSize - 1) / PAGE_SIZE

            when (buttonDirection) {
                PREV_BUTTON -> {
                    if (currentPage > 0) {
                        decreasePage()
                        loadCartProducts()
                    }
                }

                NEXT_BUTTON -> {
                    if (currentPage < lastPage) {
                        increasePage()
                        loadCartProducts()
                    }
                }
            }
        }
    }

    fun updateQuantity(
        event: Int,
        product: ProductUiModel,
    ) {
        if (event == DECREASE_BUTTON) {
            cartProductRepository.updateProduct(product.toEntity(), -1) { product ->
                _updatedItem.postValue(product?.toUiModel())
            }
        } else if (event == INCREASE_BUTTON) {
            cartProductRepository.updateProduct(product.toEntity(), 1) { product ->
                _updatedItem.postValue(product?.toUiModel())
            }
        }
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
                val pagedProducts: List<ProductUiModel> =
                    cartProducts.map { it.toUiModel() }

                _cartProducts.postValue(pagedProducts)
                checkNextButtonEnabled(totalSize)
                checkPrevButtonEnabled()
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0
        private const val PREV_BUTTON = 1
        private const val NEXT_BUTTON = 2
        private const val DECREASE_BUTTON = 0
        private const val INCREASE_BUTTON = 1
    }
}
