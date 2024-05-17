package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    fun loadPagingProduct(pagingSize: Int) {
        val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
        val pagingData = repository.loadPagingProducts(itemSize, pagingSize)
        if (pagingData.isEmpty()) throw NoSuchDataException()
        _products.value = _products.value?.plus(pagingData)
    }

    companion object {
        private const val DEFAULT_ITEM_SIZE = 0
    }
}
