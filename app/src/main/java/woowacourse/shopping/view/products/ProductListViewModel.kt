package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.Event

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel(), ProductListActionHandler {
    private val _products: MutableLiveData<PagingResult> = MutableLiveData(PagingResult(emptyList(), false))
    val products: LiveData<PagingResult> get() = _products

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>> get() = _navigateToCart

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>> get() = _navigateToDetail

    init {
        loadPagingProductData()
    }

    private fun loadPagingProductData() {
        val loadedItems = products.value?.items ?: emptyList()
        val pagingData = repository.loadPagingProducts(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)
        val hasNextPage = repository.hasNextProductPage(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)

        _products.value = PagingResult(loadedItems + pagingData, hasNextPage)
    }

    override fun onProductItemClicked(productId: Long) {
        _navigateToDetail.value = Event(productId)
    }

    override fun onShoppingCartButtonClicked() {
        _navigateToCart.value = Event(true)
    }

    override fun onMoreButtonClicked() {
        loadPagingProductData()
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
    }
}
