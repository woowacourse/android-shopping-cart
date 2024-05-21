package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingViewModel(val repository: ShoppingItemsRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val numberOfProduct: Int by lazy { repository.fetchProductsSize() }

    private val _showLoadMore = MutableLiveData<Boolean>()
    val showLoadMore: LiveData<Boolean> = _showLoadMore

    private var offset = 0

    init {
        initializeProducts()
        hideLoadMoreBtn()
    }

    fun loadNextProducts() {
        loadProducts()
        hideLoadMoreBtn()
    }

    private fun initializeProducts() {
        val nextOffSet = calculateNextOffset()
        val initialProducts = loadProducts(nextOffSet)
        if (nextOffSet != initialProducts.size) throw IllegalStateException("Something went wrong, please try again..")
        offset = nextOffSet
        _products.postValue(initialProducts)
    }

    private fun loadProducts(end: Int): List<Product> {
        return repository.fetchProductsWithIndex(end = end)
    }

    private fun loadProducts(
        start: Int,
        end: Int,
    ): List<Product> {
        return repository.fetchProductsWithIndex(start, end)
    }

    private fun getProducts(): List<Product> {
        val currentOffset = offset
        offset = calculateNextOffset()
        return loadProducts(currentOffset, offset)
    }

    private fun calculateNextOffset(): Int = Integer.min(offset + PAGE_SIZE, numberOfProduct)

    private fun loadProducts() {
        val currentProducts = products.value
        val nextProducts = getProducts()

        if (currentProducts == null) return
        _products.postValue(currentProducts + nextProducts)
    }

    fun showLoadMoreBtn() {
        _showLoadMore.postValue(true)
    }

    fun hideLoadMoreBtn() {
        _showLoadMore.postValue(false)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
