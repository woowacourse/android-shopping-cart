package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.MutableSingleLiveData
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.utils.SingleLiveData
import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _productListEvent: MutableSingleLiveData<ProductListEvent.SuccessEvent> =
        MutableSingleLiveData()
    val productListEvent: SingleLiveData<ProductListEvent.SuccessEvent> get() = _productListEvent

    private val _errorEvent: MutableSingleLiveData<ProductListEvent.ErrorEvent> =
        MutableSingleLiveData()
    val errorEvent: SingleLiveData<ProductListEvent.ErrorEvent> get() = _errorEvent

    fun loadPagingProduct() {
        try {
            val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
            val pagingData = productRepository.loadPagingProducts(itemSize)
            _products.value = _products.value?.plus(pagingData)
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ProductListEvent.LoadProductEvent.Fail
                    )

                else -> _errorEvent.postValue(ProductListEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    fun increaseShoppingCart(product: Product) {
        try {
            when (product.cartItemCounter.increase()) {
                ChangeCartItemResultState.Success -> {
                    product.cartItemCounter.selectItem()
                    when (val cartItemResult = getCartItemResult(product.id)) {
                        is CartItemResult.Exists -> {
                            shoppingCartRepository.updateCartItem(
                                cartItemResult.cartItemId,
                                product.cartItemCounter.itemCount
                            )
                        }

                        CartItemResult.NotExists -> {
                            shoppingCartRepository.addCartItem(product)
                        }
                    }
                    _productListEvent.postValue(ProductListEvent.UpdateProductEvent.Success(product.id))
                }

                ChangeCartItemResultState.Fail -> throw NoSuchDataException()
            }

        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(ProductListEvent.UpdateProductEvent.Fail)

                else -> _errorEvent.postValue(ProductListEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    fun decreaseShoppingCart(product: Product) {
        try {
            when (product.cartItemCounter.decrease()) {
                ChangeCartItemResultState.Success -> {
                    when (val cartItemResult = getCartItemResult(product.id)) {
                        is CartItemResult.Exists -> {
                            shoppingCartRepository.updateCartItem(
                                cartItemResult.cartItemId,
                                product.cartItemCounter.itemCount
                            )
                        }

                        CartItemResult.NotExists -> throw NoSuchDataException()
                    }
                    _productListEvent.postValue(ProductListEvent.UpdateProductEvent.Success(product.id))
                }

                ChangeCartItemResultState.Fail -> {
                    product.cartItemCounter.unSelectItem()
                    deleteCartItem(product)
                }
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(ProductListEvent.UpdateProductEvent.Fail)

                else -> _errorEvent.postValue(ProductListEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    private fun deleteCartItem(product: Product) {
        try {
            when (val cartItemResult = getCartItemResult(product.id)) {
                is CartItemResult.Exists -> {
                    shoppingCartRepository.deleteCartItem(cartItemResult.cartItemId)
                }

                CartItemResult.NotExists -> {
                    _errorEvent.postValue(ProductListEvent.DeleteProductEvent.Fail)
                }
            }
            product.cartItemCounter.unSelectItem()
            _productListEvent.postValue(ProductListEvent.DeleteProductEvent.Success(product.id))
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(ProductListEvent.DeleteProductEvent.Fail)

                else -> _errorEvent.postValue(ProductListEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    fun updateProducts(items: Map<Long, Int>) {
        _products.value = products.value?.map { product ->
            val count = items[product.id]
            _productListEvent.postValue(ProductListEvent.UpdateProductEvent.Success(product.id))
            if (count != null) {
                getUpdatedProduct(product,count)
            } else {
                product
            }
        }
    }

    private fun getUpdatedProduct(
        product: Product,
        count: Int,
    ): Product {
        return product.copy(
            id = product.id,
            name = product.name,
            price = product.price,
            cartItemCounter = CartItemCounter(count)
        )
    }

    private fun getCartItemResult(productId: Long): CartItemResult {
        return shoppingCartRepository.getCartItemResultFromProductId(productId = productId)
    }
}
