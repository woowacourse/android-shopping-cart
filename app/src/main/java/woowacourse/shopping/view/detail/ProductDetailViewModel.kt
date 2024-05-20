package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val productId: Long,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _cartItemSavedState: MutableLiveData<ProductDetailState> = MutableLiveData()
    val cartItemSavedState: LiveData<ProductDetailState> get() = _cartItemSavedState

    init {
        loadProductItem()
    }

    private fun loadProductItem() {
        _product.value = repository.getProduct(productId)
    }

    fun addShoppingCartItem() {
        runCatching {
            val selected = product.value ?: throw NoSuchDataException()
            repository.addCartItem(selected)
        }.onSuccess {
            _cartItemSavedState.value = ProductDetailState.Success
        }.onFailure {
            _cartItemSavedState.value = ProductDetailState.Fail
        }
    }
}
