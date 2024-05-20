package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.cart.ShoppingCartFragment.Companion.DEFAULT_ITEM_SIZE

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel(), ProductListActionHandler {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _allDataLoaded: MutableLiveData<Boolean> = MutableLiveData(false)
    val allDataLoaded: LiveData<Boolean> get() = _allDataLoaded

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>> get() = _navigateToCart

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>> get() = _navigateToDetail

    init {
        loadPagingProductData()
    }

    private fun loadPagingProductData() {
        val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
        val pagingData = repository.loadPagingProducts(itemSize, PRODUCT_LOAD_PAGING_SIZE)
        if (pagingData.isEmpty()) {
            _allDataLoaded.value = true
        } else {
            _products.value = _products.value?.plus(pagingData)
        }
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
        private const val PRODUCT_LOAD_PAGING_SIZE = 20
    }
}
