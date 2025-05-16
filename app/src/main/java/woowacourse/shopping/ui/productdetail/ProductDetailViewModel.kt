package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Product.Companion.EMPTY_PRODUCT

class ProductDetailViewModel(
    private val productsDummyRepository: ProductDummyRepositoryImpl = ProductDummyRepositoryImpl,
    private val cartDummyRepository: CartDummyRepositoryImpl = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(EMPTY_PRODUCT)
    val product: LiveData<Product> get() = _product

    fun updateProductDetail(id: Int) {
        _product.value = productsDummyRepository.fetchProductDetail(id)
    }

    fun addCartProduct() {
        cartDummyRepository.addCartProduct(product.value ?: return)
    }
}
