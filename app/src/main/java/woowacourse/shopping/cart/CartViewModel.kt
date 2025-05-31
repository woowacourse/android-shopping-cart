package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.cart.event.CartEventHandler
import woowacourse.shopping.data.cart.CartItemRepository
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.model.PagingData

class CartViewModel(
    private val repository: CartItemRepository,
) : ViewModel(),
    CartEventHandler {
    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private var currentPage: Int = INITIAL_PAGE

    private val _pagingData = MutableLiveData<PagingData>()
    val pagingData: LiveData<PagingData> = _pagingData

    init {
        loadCartProducts()
    }

    override fun onDeleteProduct(cartProduct: ProductUiModel) {
        repository.deleteCartItemById(cartProduct.id) {
            loadCartProducts()
        }
    }

    override fun onNextPage() {
        repository.getAllCartItemSize { size ->
            val lastPage = (size - 1) / PAGE_SIZE
            if (currentPage < lastPage) {
                currentPage++
                loadCartProducts()
            }
        }
    }

    override fun onPrevPage() {
        if (currentPage > 0) {
            currentPage--
            loadCartProducts()
        }
    }

    fun increaseQuantity(product: ProductUiModel) {
        val newProduct = product.copy(quantity = product.quantity + 1)
        repository.updateCartItem(newProduct) {
            _product.postValue(newProduct)
        }
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val newQuantity = if (product.quantity > 1) product.quantity - 1 else 1
        val newProduct = product.copy(quantity = newQuantity)
        repository.updateCartItem(newProduct) {
            _product.postValue(newProduct)
        }
    }

    override fun isNextButtonEnabled(): Boolean = pagingData.value?.hasNext == true

    override fun isPrevButtonEnabled(): Boolean = pagingData.value?.hasPrevious == true

    override fun isPaginationEnabled(): Boolean = (pagingData.value?.hasNext == true) || (pagingData.value?.hasPrevious == true)

    override fun getPage(): Int = currentPage

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        repository.getAllCartItemSize { totalSize ->
            var current = currentPage
            while (current > 0 && current * pageSize >= totalSize) {
                current--
            }
            val startIndex = current * pageSize
            val endIndex = minOf(startIndex + pageSize, totalSize)

            repository.subListCartItems(startIndex, endIndex) { products ->
                val hasNext = current < (totalSize - 1) / pageSize
                val hasPrevious = current > 0

                val newPagingData =
                    PagingData(
                        products = products,
                        page = current,
                        hasNext = hasNext,
                        hasPrevious = hasPrevious,
                    )

                _pagingData.postValue(newPagingData)
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0

        fun factory(repository: CartItemRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                        return CartViewModel(repository) as T
                    }
                    throw IllegalArgumentException("알 수 없는 ViewModel 클래스입니다.$modelClass")
                }
            }
    }
}
