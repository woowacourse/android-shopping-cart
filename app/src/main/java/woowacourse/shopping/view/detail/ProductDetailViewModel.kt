package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(Product.defaultProduct)
    val product: LiveData<Product> get() = _product

    fun addShoppingCartItem(product: Product) =
        thread {
            val addedCartItemId = shoppingCartRepository.addCartItem(product)
            if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        }

    fun loadProductItem(productId: Long) =
        thread {
            val loadData = productRepository.getProduct(productId)
            _product.postValue(loadData)
        }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
