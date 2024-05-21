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

    private val _errorState: MutableLiveData<ProductDetailState.ErrorState> =
        MutableLiveData()
    val errorState: LiveData<ProductDetailState.ErrorState> get() = _errorState

    private val _productDetailState = MutableLiveData<ProductDetailState>()
    val productDetailState: LiveData<ProductDetailState> = _productDetailState

    fun addShoppingCartItem(product: Product) {
        try {
            shoppingCartRepository.addCartItem(product)
            _productDetailState.value = ProductDetailState.AddShoppingCart.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorState.value = ProductDetailState.AddShoppingCart.Fail
                else -> _errorState.value = ProductDetailState.ErrorState.NotKnownError
            }
        }
    }

    fun loadProductItem(productId: Long) {
        try {
            val product = productRepository.getProduct(productId)
            product.cartItemCounter.selectItem()
            _product.postValue(product)
            _productDetailState.value = ProductDetailState.LoadProductItem.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorState.value = ProductDetailState.LoadProductItem.Fail
                else -> _errorState.value = ProductDetailState.ErrorState.NotKnownError
            }
        }
    }

    fun increaseItemCounter(){
        product.value?.cartItemCounter?.increase()
        _product.value = product.value?.cartItemCounter?.let {
            product.value?.copy(
                cartItemCounter = it
            )
        }


    }

    fun decreaseItemCounter(){
        product.value?.cartItemCounter?.decrease()
        _product.value = product.value?.cartItemCounter?.let {
            product.value?.copy(
                cartItemCounter = it
            )
        }
    }
}
