package woowacourse.shopping.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.cart.ShoppingCartFragment.Companion.DEFAULT_ITEM_SIZE
import kotlin.concurrent.thread

class MainViewModel(
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

    fun addShoppingCartItem(product: Product) =
        thread {
            val addedCartItemId = repository.addCartItem(product)
            if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        }

    fun loadProductItem(productId: Long): Product {
        return repository.getProduct(productId)
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
