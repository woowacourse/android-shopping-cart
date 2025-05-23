package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.cart.CartItemRepository
import woowacourse.shopping.data.ProductsDataSource
import kotlin.concurrent.thread

class CatalogViewModel(
    private val dataSource: ProductsDataSource,
    private val repository: CartItemRepository,
) : ViewModel() {
    private val _pagingData = MutableLiveData<PagingData>()
    val pagingData: LiveData<PagingData> = _pagingData

    private var currentPage = INITIAL_PAGE
    private var loadedProducts = emptyList<ProductUiModel>()

    private val _quantity: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_QUANTITY)
    val quantity: LiveData<Int> = _quantity

    val page: Int get() = currentPage
    val products: List<ProductUiModel> get() = loadedProducts

    private val _cartCount: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_QUANTITY)
    val cartCount: LiveData<Int> get() = _cartCount

    init {
        loadCatalogProducts()
        updateCartCount()
    }

    fun onQuantitySelectorToggled(product: ProductUiModel) {
        thread {
            val cartItem = repository.findCartItem(product)
            val cartQuantity = cartItem?.quantity ?: 0
            loadedProducts =
                loadedProducts.map {
                    if (it.id == product.id) {
                        it.copy(isExpanded = !it.isExpanded, quantity = if (cartItem == null) 1 else cartQuantity)
                    } else {
                        it
                    }
                }
            updatePaging()
            updateCartCount()
            if (cartItem == null) repository.insertCartItem(product.copy(quantity = 1, isExpanded = true))
        }
    }

    fun isProductInCart(product: ProductUiModel): Boolean {
        var isInCart = false
        thread {
            isInCart = repository.findCartItem(product) != null
        }
        return isInCart
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        currentPage += 1
        loadCatalogProducts(pageSize)
    }

    fun increaseQuantity(product: ProductUiModel) {
        thread {
            val current = findAndUpdateProduct(product.id) { it.copy(quantity = it.quantity + 1) }
            updatePaging()

            if (repository.findCartItem(product) != null) {
                repository.updateCartItem(current)
            } else {
                repository.insertCartItem(current)
            }
            updateCartCount()
        }
    }

    fun decreaseQuantity(product: ProductUiModel) {
        thread {
            val updated =
                findAndUpdateProduct(product.id) {
                    when {
                        it.quantity <= 1 -> it.copy(quantity = 0, isExpanded = false)
                        else -> it.copy(quantity = it.quantity - 1)
                    }
                }

            if (updated.quantity == 0) {
                repository.deleteCartItemById(updated.id)
            } else {
                repository.updateCartItem(updated)
            }

            updatePaging()
            updateCartCount()
        }
    }

    private fun updatePaging() {
        _pagingData.postValue(
            PagingData(
                products = loadedProducts,
                hasNext = loadedProducts.size < dataSource.getProductsSize(),
            ),
        )
    }

    private fun updateCartCount() {
        thread {
            val count = repository.getAllCartItem().sumOf { it.quantity }
            _cartCount.postValue(count)
        }
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, dataSource.getProductsSize())
        thread {
            val pagedProducts =
                dataSource
                    .getSubListedProducts(fromIndex, toIndex)
                    .map { product ->
                        val cartItem = repository.findCartItem(product)
                        if (cartItem != null && cartItem.quantity > 0) {
                            product.copy(quantity = cartItem.quantity, isExpanded = true)
                        } else {
                            product
                        }
                    }

            loadedProducts += pagedProducts
            updatePaging()
        }
    }

    private fun findAndUpdateProduct(
        id: Long,
        transform: (ProductUiModel) -> ProductUiModel,
    ): ProductUiModel {
        val updated =
            loadedProducts.map {
                if (it.id == id) transform(it) else it
            }
        loadedProducts = updated
        return updated.first { it.id == id }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_PAGE = 0
        private const val INITIAL_QUANTITY = 0

        fun factory(
            dataSource: ProductsDataSource,
            repository: CartItemRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
                        CatalogViewModel(dataSource, repository) as T
                    } else {
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
            }
    }
}
