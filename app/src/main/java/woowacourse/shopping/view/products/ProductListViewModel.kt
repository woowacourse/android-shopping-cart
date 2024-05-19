package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.cart.ShoppingCartFragment.Companion.DEFAULT_ITEM_SIZE

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel(), LoadPagingDataListener {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _allDataLoaded: MutableLiveData<Boolean> = MutableLiveData(false)
    val allDataLoaded: LiveData<Boolean> get() = _allDataLoaded

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

    override fun clickLoadPagingData() {
        loadPagingProductData()
    }

    companion object {
        private const val PRODUCT_LOAD_PAGING_SIZE = 20
    }
}
