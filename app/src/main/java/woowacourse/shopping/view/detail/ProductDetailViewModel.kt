package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.viewmodel.MainViewModel
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val repository: ProductRepository,
): ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(Product.defaultProduct)
    private val product: LiveData<Product> get() = _product

    fun addShoppingCartItem(product: Product) =
        thread {
            val addedCartItemId = repository.addCartItem(product)
            if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        }

    fun loadProductItem(productId: Long) = thread {
        val loadData = repository.getProduct(productId)
        _product.postValue(loadData)
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
