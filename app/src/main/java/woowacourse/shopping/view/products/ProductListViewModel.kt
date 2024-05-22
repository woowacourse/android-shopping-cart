package woowacourse.shopping.view.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.CartItemEntity.Companion.DEFAULT_CART_ITEM_COUNT
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.CartItemCounter.Companion.DEFAULT_ITEM_COUNT
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
    private val _cartItemCount: MutableLiveData<Int> = MutableLiveData(0)
    val cartItemCount: LiveData<Int> get() = _cartItemCount

    private val _productListEvent: MutableSingleLiveData<ProductListEvent.SuccessEvent> =
        MutableSingleLiveData()
    val productListEvent: SingleLiveData<ProductListEvent.SuccessEvent> get() = _productListEvent
    private val _errorEvent: MutableSingleLiveData<ProductListEvent.ErrorEvent> =
        MutableSingleLiveData()
    val errorEvent: SingleLiveData<ProductListEvent.ErrorEvent> get() = _errorEvent

    init {
        updateTotalCount()
    }

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
            when (val cartItemResult = getCartItemResult(product.id)) {
                is CartItemResult.Exists -> {
                    when (cartItemResult.counter.increase()) {
                        ChangeCartItemResultState.Success -> {
                            shoppingCartRepository.updateCartItem(
                                cartItemResult.cartItemId,
                                cartItemResult.counter.itemCount,
                            )
                        }

                        ChangeCartItemResultState.Fail -> throw NoSuchDataException()
                    }
                    product.cartItemCounter.updateCount(cartItemResult.counter.itemCount)
                }

                CartItemResult.NotExists -> {
                    shoppingCartRepository.addCartItem(product)
                    product.cartItemCounter.updateCount(DEFAULT_CART_ITEM_COUNT)
                }
            }
            product.cartItemCounter.selectItem()
            _cartItemCount.value = _cartItemCount.value?.plus(DEFAULT_CART_ITEM_COUNT)
            _productListEvent.postValue(ProductListEvent.UpdateProductEvent.Success(product.id))
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
            when (val cartItemResult = getCartItemResult(product.id)) {
                is CartItemResult.Exists -> {
                    when (product.cartItemCounter.decrease()) {
                        ChangeCartItemResultState.Success -> {
                            shoppingCartRepository.updateCartItem(
                                cartItemResult.cartItemId,
                                product.cartItemCounter.itemCount
                            )
                        }

                        ChangeCartItemResultState.Fail -> {
                            deleteCartItem(product, cartItemResult.cartItemId)
                        }
                    }
                }

                CartItemResult.NotExists -> throw NoSuchDataException()
            }
            _cartItemCount.value = _cartItemCount.value?.minus(DEFAULT_CART_ITEM_COUNT)
            _productListEvent.postValue(ProductListEvent.UpdateProductEvent.Success(product.id))

        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(ProductListEvent.UpdateProductEvent.Fail)

                else -> _errorEvent.postValue(ProductListEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    private fun deleteCartItem(
        product: Product,
        cartItemId: Long,
    ) {
        try {
            shoppingCartRepository.deleteCartItem(cartItemId)
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

    private fun updateTotalCount() {
        _cartItemCount.value = shoppingCartRepository.getTotalCartItemCount()
    }

    fun updateProducts(items: Map<Long, Int>) {
        products.value?.forEach {
            val count = items[it.id]
            if (count != null){
                if (count == DEFAULT_ITEM_COUNT) it.cartItemCounter.unSelectItem()
                it.cartItemCounter.updateCount(count)
                _productListEvent.postValue(ProductListEvent.UpdateProductEvent.Success(it.id))
            }
        }
        updateTotalCount()
    }

    private fun getCartItemResult(productId: Long): CartItemResult {
        return shoppingCartRepository.getCartItemResultFromProductId(productId = productId)
    }
}
