package woowacourse.shopping.presentation.viewmodel.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductDummyRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.util.SingleLiveEvent

class ProductDetailViewModel(
    private val productsDummyRepository: ProductRepository = ProductDummyRepositoryImpl,
    private val cartDummyRepository: CartRepository = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _product: MutableLiveData<Product> =
        MutableLiveData(Product.Companion.INVALID_PRODUCT)
    val product: LiveData<Product> get() = _product

    private val _count: MutableLiveData<Int> = MutableLiveData(0)
    val count: LiveData<Int> get() = _count

    val putProductFlag: SingleLiveEvent<Unit> = SingleLiveEvent()
    val finishFlag: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun updateProductDetail(id: Int) {
        _product.value = productsDummyRepository.fetchProductDetail(id)
    }

    fun addCartProduct() {
        cartDummyRepository.upsertCartProduct(product.value ?: return, _count.value ?: return)
        putProductFlag.call()
        finishFlag.call()
    }

    fun upCount() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun downCount() {
        _count.value = (_count.value ?: 0) - 1
        if ((_count.value ?: 0) <= 0) _count.value = 0
    }
}
