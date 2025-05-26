package woowacourse.shopping.product.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartItemRepository
import woowacourse.shopping.data.product.ProductsDataSource
import woowacourse.shopping.data.recent.ViewedItemRepository
import woowacourse.shopping.product.catalog.model.PagingData

class CatalogViewModel(
    private val dataSource: ProductsDataSource,
    private val cartRepository: CartItemRepository,
    private val viewedRepository: ViewedItemRepository,
) : ViewModel() {
    private val _pagingData = MutableLiveData<PagingData>()
    val pagingData: LiveData<PagingData> = _pagingData

    private val _cartCount = MutableLiveData(0)
    val cartCount: LiveData<Int> = _cartCount

    private val _recentViewedItems = MutableLiveData<List<ProductUiModel>>()
    val recentViewedItems: LiveData<List<ProductUiModel>> = _recentViewedItems

    private val _hasRecentViewedItems = MutableLiveData(false)
    val hasRecentViewedItems: LiveData<Boolean> = _hasRecentViewedItems

    private val loadedProducts = mutableListOf<ProductUiModel>()
    private var currentPage = 0
    val page: Int get() = currentPage

    init {
        loadCatalogProducts()
        loadRecentViewedItems()
        updateCartCount()
    }

    fun onQuantitySelectorToggled(product: ProductUiModel) {
        val toggled = product.copy(isExpanded = !product.isExpanded, quantity = product.quantity + 1)
        updateProduct(toggled)
    }

    fun increaseQuantity(product: ProductUiModel) {
        updateProduct(product.copy(quantity = product.quantity + 1))
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val newQuantity = (product.quantity - 1).coerceAtLeast(0)
        val updated =
            product.copy(
                quantity = newQuantity,
                isExpanded = newQuantity > 0,
            )
        updateProduct(updated)
    }

    private fun updateProduct(updated: ProductUiModel) {
        if (updated.quantity == 0) {
            cartRepository.deleteCartItemById(updated.id) {
                applyProductChange(updated)
            }
        } else {
            cartRepository.updateOrInsertItem(updated) {
                applyProductChange(updated)
            }
        }
    }

    private fun applyProductChange(updated: ProductUiModel) {
        val index = loadedProducts.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            loadedProducts[index] = updated
        }
        updatePaging()
        updateCartCount()
    }

    fun loadNextCatalogProducts() {
        currentPage++
        loadCatalogProducts()
    }

    private fun loadCatalogProducts(pageSize: Int = PAGE_SIZE) {
        val from = currentPage * pageSize
        val to = minOf(from + pageSize, dataSource.getProductsSize())
        val subList = dataSource.getSubListedProducts(from, to)

        val tempList = subList.toMutableList()
        var remaining = tempList.size

        tempList.forEachIndexed { idx, product ->
            cartRepository.findCartItem(product) { cartItem ->
                val updated =
                    if (cartItem != null) {
                        product.copy(quantity = cartItem.quantity, isExpanded = true)
                    } else {
                        product
                    }

                synchronized(tempList) {
                    tempList[idx] = updated
                    remaining--
                    if (remaining == 0) {
                        loadedProducts.addAll(tempList)
                        updatePaging()
                    }
                }
            }
        }
    }

    fun loadRecentViewedItems() {
        viewedRepository.getViewedItems { items ->
            _recentViewedItems.postValue(items)
            _hasRecentViewedItems.postValue(items.isNotEmpty())
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
        cartRepository.getAllCartItem { items ->
            _cartCount.postValue(items.sumOf { it.quantity })
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun factory(
            dataSource: ProductsDataSource,
            repository: CartItemRepository,
            viewedRepository: ViewedItemRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
                        CatalogViewModel(dataSource, repository, viewedRepository) as T
                    } else {
                        throw IllegalArgumentException("알 수 없는 ViewModel 클래스입니다.$modelClass")
                    }
            }
    }
}
