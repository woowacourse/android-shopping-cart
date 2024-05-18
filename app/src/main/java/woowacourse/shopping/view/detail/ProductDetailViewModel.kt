package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val productId: Long,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _cartItemSavedState: MutableLiveData<ProductDetailState> = MutableLiveData(ProductDetailState.Idle)
    val cartItemSavedState: LiveData<ProductDetailState> get() = _cartItemSavedState

    init {
        loadProductItem()
    }

    private fun loadProductItem() {
        _product.value = repository.getProduct(productId)
    }

    fun addShoppingCartItem(product: Product) {
        runCatching {
            repository.addCartItem(product)
        }.onSuccess {
            _cartItemSavedState.value = ProductDetailState.Success
        }.onFailure {
            _cartItemSavedState.value = ProductDetailState.Fail
        }
        _cartItemSavedState.value = ProductDetailState.Idle
    }
}
