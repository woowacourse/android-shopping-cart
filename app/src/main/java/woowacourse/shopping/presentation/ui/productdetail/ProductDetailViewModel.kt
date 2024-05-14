package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productListRepository: ProductListRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    init {
        savedStateHandle.get<Int>(PUT_EXTRA_PRODUCT_ID)?.let { productId ->
            findByProductId(productId)
        }
    }

    private fun findByProductId(id: Int) {
        productListRepository.findProductById(id).onSuccess { productValue ->
            _product.value = productValue
        }.onFailure {
            // TODO 예외 처리 예정
        }
    }

    fun onAddToCartButtonClick() {
        product.value?.let { product ->
            shoppingCartRepository.addOrder(product)
        }
    }
}
