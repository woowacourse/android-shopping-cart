package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem.Companion.DEFAULT_CART_ITEM_ID
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.CartItemCounter.Companion.DEFAULT_ITEM_COUNT
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Product.Companion.DEFAULT_PRODUCT_ID
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.MutableSingleLiveData
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.utils.SingleLiveData

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentlyProductRepository: RecentlyProductRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData(Product.defaultProduct)
    val product: LiveData<Product> get() = _product
    private var cartItemId: Long = DEFAULT_CART_ITEM_ID

    private val _recentlyProduct: MutableLiveData<RecentlyProduct> =
        MutableLiveData(RecentlyProduct.defaultRecentlyProduct)
    val recentlyProduct: LiveData<RecentlyProduct> get() = _recentlyProduct

    private val _errorEvent: MutableSingleLiveData<ProductDetailEvent.ErrorEvent> =
        MutableSingleLiveData()
    val errorEvent: SingleLiveData<ProductDetailEvent.ErrorEvent> get() = _errorEvent

    private val _productDetailEvent = MutableSingleLiveData<ProductDetailEvent.SuccessEvent>()
    val productDetailEvent: SingleLiveData<ProductDetailEvent.SuccessEvent> = _productDetailEvent

    fun addShoppingCartItem(product: Product) {
        try {
            checkValidProduct(product)
            when (cartItemId) {
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
            _productDetailEvent.postValue(
                ProductDetailEvent.AddShoppingCart.Success(
                    productId = product.id,
                    count = product.cartItemCounter.itemCount,
                ),
            )
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ProductDetailEvent.AddShoppingCart.Fail,
                    )

                else ->
                    _errorEvent.postValue(
                        ProductDetailEvent.ErrorEvent.NotKnownError,
                    )
            }
        }
    }

    fun loadProductItem(productId: Long) {
        try {
            val loadItemCounter = loadProductItemCount(productId)
            val product = productRepository.getProduct(productId)
            product.itemSelector.selectItem()
            product.cartItemCounter.updateCount(loadItemCounter.itemCount)
            loadRecentlyProduct(product)
            _product.value = product
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ProductDetailEvent.LoadProductItem.Fail,
                    )

                else ->
                    _errorEvent.postValue(
                        ProductDetailEvent.ErrorEvent.NotKnownError,
                    )
            }
        }
    }

    private fun loadProductItemCount(productId: Long): CartItemCounter {
        return try {
            when (
                val result =
                    shoppingCartRepository.getCartItemResultFromProductId(productId = productId)
            ) {
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
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ProductDetailEvent.LoadProductItem.Fail,
                    )

                else ->
                    _errorEvent.postValue(
                        ProductDetailEvent.ErrorEvent.NotKnownError,
                    )
            }
            CartItemCounter()
        }
    }

    fun increaseItemCounter() {
        product.value?.cartItemCounter?.increase()
        _product.value =
            product.value?.cartItemCounter?.let {
                product.value?.copy(
                    cartItemCounter = it,
                )
            }
    }

    fun decreaseItemCounter() {
        val productCount = product.value?.cartItemCounter?.itemCount ?: DEFAULT_ITEM_COUNT
        if (productCount > DEFAULT_ITEM_COUNT) {
            product.value?.cartItemCounter?.decrease()
            _product.value =
                product.value?.cartItemCounter?.let {
                    product.value?.copy(
                        cartItemCounter = it,
                    )
                }
        }
    }

    private fun saveRecentlyProduct(product: Product) {
        recentlyProductRepository.addRecentlyProduct(
            RecentlyProduct(
                productId = product.id,
                imageUrl = product.imageUrl,
                name = product.name,
            ),
        )
    }

    private fun deletePrevRecentlyProduct(recentlyProductId: Long) {
        recentlyProductRepository.deleteRecentlyProduct(recentlyProductId)
    }

    fun updateRecentlyProduct(recentlyProduct: RecentlyProduct) {
        try {
            deletePrevRecentlyProduct(recentlyProduct.id)
            val loadItemCounter = loadProductItemCount(recentlyProduct.productId)
            val product = productRepository.getProduct(recentlyProduct.productId)
            product.itemSelector.selectItem()
            product.cartItemCounter.updateCount(loadItemCounter.itemCount)
            _product.value = product
            _recentlyProduct.value = RecentlyProduct.defaultRecentlyProduct
            _productDetailEvent.postValue(ProductDetailEvent.UpdateRecentlyProductItem.Success)
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorEvent.postValue(
                        ProductDetailEvent.LoadProductItem.Fail,
                    )

                else ->
                    _errorEvent.postValue(
                        ProductDetailEvent.ErrorEvent.NotKnownError,
                    )
            }
        }
    }

    private fun loadRecentlyProduct(product: Product) {
        try {
            val recentlyProduct = recentlyProductRepository.getMostRecentlyProduct()
            _recentlyProduct.value = recentlyProduct
            _productDetailEvent.postValue(ProductDetailEvent.UpdateRecentlyProductItem.Success)
            if (recentlyProduct.productId != product.id) {
                saveRecentlyProduct(product)
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorEvent.postValue(ProductDetailEvent.UpdateRecentlyProductItem.Fail)
                else -> _errorEvent.postValue(ProductDetailEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    private fun checkValidProduct(product: Product) {
        if (product.id == DEFAULT_PRODUCT_ID) throw NoSuchDataException()
        if (product.cartItemCounter.itemCount == DEFAULT_ITEM_COUNT) throw NoSuchDataException()
    }
}
