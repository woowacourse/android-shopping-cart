package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

class ShoppingCartViewModel(
    private val repository: CartProductRepository,
) : ViewModel(),
    ShoppingCartEventHandler {
    private val cachedProducts = mutableListOf<CartProduct>()

    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>> = _products

    private var _page = MutableLiveData(FIRST_PAGE_NUMBER)
    val page: LiveData<Int> = _page

    private val _hasNext = MutableLiveData(false)
    val hasNext: LiveData<Boolean> = _hasNext

    private val _hasPrevious = MutableLiveData(false)
    val hasPrevious: LiveData<Boolean> = _hasPrevious

    private val _isSinglePage = MutableLiveData(true)
    val isSinglePage: LiveData<Boolean> = _isSinglePage

    init {
        loadPage(FIRST_PAGE_NUMBER)
    }

    override fun loadNextProducts() {
        val currentPage = page.value ?: FIRST_PAGE_NUMBER
        if (hasNext.value != true) return
        val nextPage = currentPage + 1
        loadPage(nextPage)
    }

    override fun loadPreviousProducts() {
        val currentPage = page.value ?: FIRST_PAGE_NUMBER
        if (hasPrevious.value != true) return
        val previousPage = currentPage - 1
        loadPage(previousPage)
    }

    override fun onProductRemoveClick(item: CartProduct) {
        deleteProduct(item)
    }

    override fun onQuantityIncreaseClick(item: Product) {
        val currentQuantity = cachedProducts.find { it.product.id == item.id }?.quantity ?: 0
        val newQuantity = currentQuantity + 1
        repository.updateQuantity(item.id, newQuantity)
        updateProductQuantity(item, newQuantity)
    }

    override fun onQuantityDecreaseClick(item: Product) {
        val currentQuantity = cachedProducts.find { it.product.id == item.id }?.quantity ?: 0
        val newQuantity = currentQuantity - 1
        if (currentQuantity == 1) {
            repository.deleteByProductId(item.id)
        } else {
            repository.updateQuantity(item.id, newQuantity)
        }
        updateProductQuantity(item, newQuantity)
    }

    private fun loadPage(page: Int) {
        val offset = (page - 1) * PAGE_SIZE
        val end = offset + PAGE_SIZE

        if (cachedProducts.size < end) {
            val result = repository.getPagedProducts(end - cachedProducts.size, cachedProducts.size)
            cachedProducts.addAll(result.items)
            _hasNext.value = result.hasNext
        } else {
            _hasNext.value = cachedProducts.size > end || checkHasNext(end)
        }

        _products.value = cachedProducts.subList(offset, cachedProducts.size.coerceAtMost(end))
        _hasPrevious.value = page > FIRST_PAGE_NUMBER
        _page.value = page
        _isSinglePage.value =
            page == FIRST_PAGE_NUMBER &&
            hasNext.value == false &&
            cachedProducts.size <= PAGE_SIZE
    }

    private fun deleteProduct(product: CartProduct) {
        repository.deleteByProductId(product.product.id)
        cachedProducts.remove(product)

        val currentPage = page.value ?: FIRST_PAGE_NUMBER
        loadPage(currentPage)

        if (products.value.isNullOrEmpty() && currentPage > FIRST_PAGE_NUMBER) {
            val previousPage = currentPage - 1
            loadPage(previousPage)
            _page.value = previousPage
        }
    }

    private fun checkHasNext(offset: Int): Boolean {
        val result = repository.getPagedProducts(1, offset)
        return result.items.isNotEmpty()
    }

    private fun updateProductQuantity(
        product: Product,
        quantity: Int,
    ) {
        val index = cachedProducts.indexOfFirst { it.product.id == product.id }
        val oldProduct = cachedProducts[index]
        if (quantity == 0) {
            cachedProducts.removeAt(index)
        } else {
            cachedProducts[index] = oldProduct.copy(quantity = quantity)
        }
        loadPage(page.value ?: FIRST_PAGE_NUMBER)
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
