package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Product.Companion.INVALID_PRODUCT
import woowacourse.shopping.util.SingleLiveEvent

class ProductDetailViewModel(
    private val productsDummyRepository: ProductDummyRepositoryImpl = ProductDummyRepositoryImpl,
    private val cartDummyRepository: CartDummyRepositoryImpl = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(INVALID_PRODUCT)
    val product: LiveData<Product> get() = _product

    val putProductFlag: SingleLiveEvent<Unit> = SingleLiveEvent()
    val finishFlag: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun updateProductDetail(id: Int) {
        _product.value = productsDummyRepository.fetchProductDetail(id)
    }

    fun addCartProduct() {
        cartDummyRepository.addCartProduct(product.value ?: return)
        putProductFlag.call()
        finishFlag.call()
    }
}
