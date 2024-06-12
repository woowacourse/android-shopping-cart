package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel
import woowacourse.shopping.presentation.shopping.toShoppingUiModel
import woowacourse.shopping.presentation.util.SingleLiveEvent

class ProductDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val productId: Long,
) : ViewModel(), DetailAction {
    private val _product = MutableLiveData<ShoppingUiModel.Product>()
    val product: LiveData<ShoppingUiModel.Product> get() = _product

    private val _isAddedCart = SingleLiveEvent<Boolean>()
    val isAddedCart: LiveData<Boolean> get() = _isAddedCart

    private val _lastViewedProduct = MutableLiveData<RecentProduct?>()
    val lastViewedProduct: LiveData<RecentProduct?> get() = _lastViewedProduct

    private val _onClickedLastViewedProduct = SingleLiveEvent<Long>()
    val onClickedLastViewedProduct: LiveData<Long> get() = _onClickedLastViewedProduct

    val isRecentProductVisible: LiveData<Boolean> =
        _product.map {
            lastViewedProduct.value != null &&
                it.id != lastViewedProduct.value?.product?.id
        }

    init {
        loadProduct()
        loadLastViewedProduct()
    }

    private fun loadProduct() {
        val product =
            shoppingRepository.productById(productId)?.toShoppingUiModel(true) ?: return
        _product.value = product
    }

    private fun loadLastViewedProduct() {
        val recentProduct = recentProductRepository.recentProducts(2).last()
        _lastViewedProduct.value = recentProduct
    }

    fun navigateToRecentProduct() {
        val recentId = _lastViewedProduct.value?.product?.id ?: return
        _onClickedLastViewedProduct.value = recentId
    }

    fun addCartProduct() {
        val product = _product.value ?: return
        cartRepository.addCartProduct(product.id, product.count)
        _isAddedCart.value = true
    }

    override fun onPlus(cartProduct: ShoppingUiModel.Product) {
        val product = _product.value ?: return
        if (product.id == cartProduct.id) {
            val newCount = product.count + 1
            _product.value = product.copy(count = newCount)
        }
    }

    override fun onMinus(cartProduct: ShoppingUiModel.Product) {
        val product = _product.value ?: return
        if (product.id == cartProduct.id) {
            val newCount = (product.count - 1).coerceAtLeast(1)
            _product.value = product.copy(count = newCount)
        }
    }

    companion object {
        fun factory(
            shoppingRepository: ShoppingRepository,
            cartRepository: CartRepository,
            recentProductRepository: RecentProductRepository,
            productId: Long,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    shoppingRepository,
                    cartRepository,
                    recentProductRepository,
                    productId,
                )
            }
        }
    }
}
