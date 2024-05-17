package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

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
        runCatching {
            shoppingCartRepository.addCartItem(product)
        }.onSuccess {
            _productDetailState.value = ProductDetailState.AddShoppingCart.Success
            _productDetailState.value = ProductDetailState.Init
        }.onFailure {
            _productDetailState.value = ProductDetailState.AddShoppingCart.Fail
            _productDetailState.value = ProductDetailState.Init
        }
    }

    fun loadProductItem(productId: Long) {
        runCatching {
            productRepository.getProduct(productId)
        }
            .onSuccess {
                _productDetailState.value = ProductDetailState.LoadProductItem.Success
                _product.postValue(it)
                _productDetailState.value = ProductDetailState.Init
            }
            .onFailure {
                _productDetailState.value = ProductDetailState.LoadProductItem.Fail
                _productDetailState.value = ProductDetailState.Init
            }
    }
}
