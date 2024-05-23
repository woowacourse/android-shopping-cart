package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.event.Event
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(val repository: ShoppingItemsRepository) : ViewModel(), ShoppingEventHandler {
    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>>
        get() = _products

    private val _canLoadMore = MutableLiveData(false)
    val canLoadMore: LiveData<Boolean>
        get() = _canLoadMore

    private val _shoppingUiState = MutableLiveData<UIState<List<Product>>>(UIState.Empty)
    val shoppingUiState: LiveData<UIState<List<Product>>>
        get() = _shoppingUiState

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private val numberOfProduct: Int by lazy { repository.fetchProductsSize() }

    private val _showLoadMore = MutableLiveData<Boolean>(false)
    val showLoadMore: LiveData<Boolean> = _showLoadMore

    private var offset = 0

    init {
        initializeProducts()
        hideLoadMore()
    }

    fun loadNextProducts() {
        loadProducts()
        hideLoadMore()
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

    fun showLoadMoreByCondition() {
        if (offset != numberOfProduct) showLoadMore()
    }

    fun showLoadMore() {
        _showLoadMore.postValue(true)
    }

    fun hideLoadMore() {
        _showLoadMore.postValue(false)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }

    override fun onProductClick(productId: Long) {
        _navigateToDetail.postValue(Event(productId))
    }

    override fun onLoadMoreButtonClick() {
        TODO("Not yet implemented")
    }

    override fun onShoppingCartButtonClick() {
        _navigateToCart.postValue(Event(true))
    }
}
