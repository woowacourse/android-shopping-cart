package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.CartQuantityActionHandler

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val recentlyViewedProductsRepository: RecentlyViewedProductsRepository,
    private val productId: Long,
) : ViewModel(), CartQuantityActionHandler {
    private val _productWithQuantity = MutableLiveData<ProductWithQuantity>()
    private lateinit var product: ProductWithQuantity
    val productWithQuantity: LiveData<ProductWithQuantity> get() = _productWithQuantity

    private val _addToCart = MutableLiveData<Long>()
    val addToCart: LiveData<Long> get() = _addToCart

    private val _recentlyViewedProduct = MutableLiveData<RecentlyViewedProduct?>()
    val recentlyViewedProduct: LiveData<RecentlyViewedProduct?> get() = _recentlyViewedProduct

    init {
        loadProductData()
    }

    private fun loadProductData() {
        product =
            shoppingRepository.productWithQuantityItem(productId)?.copy(
                quantity =
                    shoppingRepository.productWithQuantityItem(productId)?.quantity?.takeIf { it > 0 }
                        ?: 1,
            ) ?: return
        _productWithQuantity.postValue(product)

        loadRecentlyProduct()
        setRecentlyProduct()
    }

    private fun loadRecentlyProduct() {
        val recentlyViewedProducts = recentlyViewedProductsRepository.getRecentlyViewedProducts(1)
        if (recentlyViewedProducts.isNotEmpty()) {
            val lastViewedProduct = recentlyViewedProducts[0]
            if (lastViewedProduct.productId != productId) {
                _recentlyViewedProduct.postValue(lastViewedProduct)
            } else {
                _recentlyViewedProduct.postValue(null)
            }
        }
    }

    private fun setRecentlyProduct() {
        val recentlyViewedProduct =
            RecentlyViewedProduct(
                productId = product.product.id,
                name = product.product.name,
                price = product.product.price,
                imageUrl = product.product.imageUrl,
                viewedAt = System.currentTimeMillis(),
            )
        recentlyViewedProductsRepository.insertRecentlyViewedProduct(recentlyViewedProduct)
    }

    fun onAddToCartClicked(productId: Long) {
        createShoppingCartItem()
        _addToCart.postValue(productId)
    }

    private fun createShoppingCartItem() {
        productWithQuantity.value?.let {
            cartRepository.insert(
                productWithQuantity = it,
            )
        }
    }

    override fun onPlusButtonClicked(productId: Long) {
        productWithQuantity.value?.let {
            it.quantity.takeIf { quantity -> quantity < 100 } ?: return
            _productWithQuantity.postValue(it.copy(quantity = it.quantity + 1))
        }
    }

    override fun onMinusButtonClicked(productId: Long) {
        productWithQuantity.value?.let {
            it.quantity.takeIf { quantity -> quantity > 1 } ?: return
            _productWithQuantity.postValue(it.copy(quantity = it.quantity - 1))
        }
    }
}
