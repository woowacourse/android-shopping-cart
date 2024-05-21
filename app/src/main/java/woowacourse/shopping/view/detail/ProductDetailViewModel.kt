package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.ERROR_DATA_ID
import woowacourse.shopping.domain.model.CartItem.Companion.DEFAULT_CART_ITEM_ID
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.CartItemCounter.Companion.DEFAULT_ITEM_COUNT
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Product.Companion.DEFAULT_PRODUCT_ID
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(Product.defaultProduct)
    val product: LiveData<Product> get() = _product
    private var cartItemId: Long = DEFAULT_CART_ITEM_ID

    private val _errorEvent: MutableLiveData<ProductDetailEvent.ErrorEvent> =
        MutableLiveData()
    val errorEvent: LiveData<ProductDetailEvent.ErrorEvent> get() = _errorEvent

    private val _productDetailEvent = MutableLiveData<ProductDetailEvent.SuccessEvent>()
    val productDetailEvent: LiveData<ProductDetailEvent.SuccessEvent> = _productDetailEvent

    fun addShoppingCartItem(product: Product) {
        try {
            checkValidProduct(product)
            when(cartItemId){
                DEFAULT_CART_ITEM_ID -> {
                    shoppingCartRepository.addCartItem(product)
                }
                else -> {
                    shoppingCartRepository.updateCartItem(
                        cartItemId,
                        product.cartItemCounter.itemCount,
                    )
                }
            }
            _productDetailEvent.value = ProductDetailEvent.AddShoppingCart.Success(
                productId = product.id,
                count = product.cartItemCounter.itemCount
            )
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorEvent.value =
                    ProductDetailEvent.AddShoppingCart.Fail

                else -> _errorEvent.value = ProductDetailEvent.ErrorEvent.NotKnownError
            }
        }
    }

    fun loadProductItem(productId: Long) {
        try {
            val loadItemCounter = loadProductItemCount(productId)
            val product = productRepository.getProduct(productId)
            product.cartItemCounter.selectItem()
            product.cartItemCounter.updateCount(loadItemCounter.itemCount)
            _product.value = product
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorEvent.value =
                    ProductDetailEvent.LoadProductItem.Fail

                else -> _errorEvent.value = ProductDetailEvent.ErrorEvent.NotKnownError
            }
        }
    }

    private fun loadProductItemCount(productId: Long): CartItemCounter {
        return try {
            when (val result = shoppingCartRepository.getCartItemResultFromProductId(productId = productId)) {
                is CartItemResult.Exists -> {
                    cartItemId = result.cartItemId
                    result.counter
                }
                CartItemResult.NotExists -> {
                     CartItemCounter()
                }
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorEvent.value =
                    ProductDetailEvent.LoadProductItem.Fail

                else -> _errorEvent.value = ProductDetailEvent.ErrorEvent.NotKnownError
            }
            CartItemCounter()
        }
    }

    fun increaseItemCounter() {
        product.value?.cartItemCounter?.increase()
        _product.value = product.value?.cartItemCounter?.let {
            product.value?.copy(
                cartItemCounter = it
            )
        }
    }

    fun decreaseItemCounter() {
        product.value?.cartItemCounter?.decrease()
        _product.value = product.value?.cartItemCounter?.let {
            product.value?.copy(
                cartItemCounter = it
            )
        }
    }

    private fun checkValidProduct(product: Product){
        if (product.id == DEFAULT_PRODUCT_ID) throw NoSuchDataException()
        if (product.cartItemCounter.itemCount == DEFAULT_ITEM_COUNT) throw NoSuchDataException()
    }
}
