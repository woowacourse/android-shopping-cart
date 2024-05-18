package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(Product.defaultProduct)
    val product: LiveData<Product> get() = _product

    private val _productDetailState: MutableLiveData<ProductDetailState> =
        MutableLiveData(ProductDetailState.Init)
    val productDetailState: LiveData<ProductDetailState> get() = _productDetailState

    fun addShoppingCartItem(product: Product) {
        try {
            shoppingCartRepository.addCartItem(product)
            _productDetailState.value = ProductDetailState.AddShoppingCart.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _productDetailState.value = ProductDetailState.AddShoppingCart.Fail
                else -> _productDetailState.value = ProductDetailState.Error
            }
        } finally {
            resetState()
        }
    }

    fun loadProductItem(productId: Long) {
        try {
            val product = productRepository.getProduct(productId)
            _product.postValue(product)
            _productDetailState.value = ProductDetailState.LoadProductItem.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _productDetailState.value = ProductDetailState.LoadProductItem.Fail
                else -> _productDetailState.value = ProductDetailState.Error
            }
        } finally {
            resetState()
        }
    }

    private fun resetState() {
        _productDetailState.value = ProductDetailState.Init
    }
}
