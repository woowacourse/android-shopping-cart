package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartItem
import woowacourse.shopping.data.CartItemRepository
import woowacourse.shopping.data.ProductsDataSource
import woowacourse.shopping.product.catalog.model.PagingData

class CatalogViewModel(
    private val dataSource: ProductsDataSource,
    private val repository: CartItemRepository,
) : ViewModel() {
    private val _pagingData = MutableLiveData<PagingData>()
    val pagingData: LiveData<PagingData> = _pagingData

    private val loadedProducts: MutableList<ProductUiModel> = mutableListOf()
    val products: List<ProductUiModel> get() = loadedProducts.toList()

    private val _quantity = MutableLiveData(INITIAL_QUANTITY)
    val quantity: LiveData<Int> = _quantity

    private val _cartCount = MutableLiveData(INITIAL_QUANTITY)
    val cartCount: LiveData<Int> = _cartCount

    val page: Int get() = currentPage

    private var currentPage = INITIAL_PAGE

    init {
        loadCatalogProducts()
        updateCartCount()
    }

    fun onQuantitySelectorToggled(product: ProductUiModel) {
        repository.findCartItem(product) { cartItem ->
            val index = loadedProducts.indexOfFirst { it.id == product.id }
            if (index != -1) {
                val quantity = cartItem?.quantity ?: 1
                val toggled =
                    loadedProducts[index].copy(
                        isExpanded = !loadedProducts[index].isExpanded,
                        quantity = quantity,
                    )
                loadedProducts[index] = toggled
            }

            if (cartItem == null) {
                repository.insertCartItem(product.copy(quantity = 1, isExpanded = true)) {
                    updatePaging()
                    updateCartCount()
                }
            } else {
                updatePaging()
                updateCartCount()
            }
        }
    }

    fun loadNextCatalogProducts(pageSize: Int = PAGE_SIZE) {
        currentPage++
        loadCatalogProducts(pageSize)
    }

    fun increaseQuantity(product: ProductUiModel) {
        repository.findCartItem(product) { cartItem ->
            val updated = findAndUpdateProduct(product.id) { it.copy(quantity = it.quantity + 1) }
            updatePaging()
            upsertCartItem(updated, cartItem)
            updateCartCount()
        }
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val updated =
            findAndUpdateProduct(product.id) {
                if (it.quantity <= 1) {
                    it.copy(quantity = 0, isExpanded = false)
                } else {
                    it.copy(quantity = it.quantity - 1)
                }
            }

        if (updated.quantity == 0) {
            repository.deleteCartItemById(updated.id) {
                updatePaging()
                updateCartCount()
            }
        } else {
            repository.updateCartItem(updated) {
                updatePaging()
                updateCartCount()
            }
        }
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, dataSource.getProductsSize())
        val subList = dataSource.getSubListedProducts(fromIndex, toIndex)

        var remaining = subList.size
        if (remaining == 0) {
            loadedProducts.clear()
            updatePaging()
            return
        }

        val updated = MutableList(subList.size) { index -> subList[index] }

        subList.forEachIndexed { index, product ->
            repository.findCartItem(product) { cartItem ->
                val updatedProduct =
                    if ((cartItem?.quantity ?: 0) > 0) {
                        product.copy(quantity = cartItem!!.quantity, isExpanded = true)
                    } else {
                        product
                    }

                synchronized(updated) {
                    updated[index] = updatedProduct
                    remaining--
                    if (remaining == 0) {
                        loadedProducts.addAll(updated)
                        updatePaging()
                    }
                }
            }
        }
    }

    private fun updatePaging() {
        _pagingData.postValue(
            PagingData(
                products = loadedProducts.toList(),
                hasNext = loadedProducts.size < dataSource.getProductsSize(),
            ),
        )
    }

    private fun updateCartCount() {
        repository.getAllCartItem { items ->
            _cartCount.postValue(items.sumOf { it.quantity })
        }
    }

    private fun findAndUpdateProduct(
        id: Long,
        transform: (ProductUiModel) -> ProductUiModel,
    ): ProductUiModel {
        val index = loadedProducts.indexOfFirst { it.id == id }
        if (index < 0) throw IllegalArgumentException("Product not found: id=$id")
        val updated = transform(loadedProducts[index])
        loadedProducts[index] = updated
        return updated
    }

    private fun upsertCartItem(
        product: ProductUiModel,
        existingItem: CartItem?,
    ) {
        if (existingItem != null) {
            repository.updateCartItem(product) {
                updateCartCount()
            }
        } else {
            repository.insertCartItem(product) {
                updateCartCount()
            }
        }
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
