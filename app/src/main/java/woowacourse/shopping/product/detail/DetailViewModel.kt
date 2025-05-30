package woowacourse.shopping.product.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.cart.ButtonEvent
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.data.repository.CartProductRepository
import woowacourse.shopping.data.repository.RecentlyViewedProductRepository
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailViewModel(
    productData: ProductUiModel,
    private val cartProductRepository: CartProductRepository,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel>(productData)
    val product: LiveData<ProductUiModel> = _product

    private val _quantity = MutableLiveData<Int>(productData.quantity)
    val quantity: LiveData<Int> = _quantity

    private val _price = MutableLiveData<Int>(productData.price)
    val price: LiveData<Int> = _price

    private val _latestViewedProduct = MutableLiveData<ProductUiModel>()
    val latestViewedProduct: LiveData<ProductUiModel> = _latestViewedProduct

    init {
        product.value?.id?.let {
            recentlyViewedProductRepository.insertRecentlyViewedProductUid(it)
        }
    }

    fun updateQuantity(buttonEvent: ButtonEvent) {
        when (buttonEvent) {
            ButtonEvent.INCREASE -> increaseQuantity()
            ButtonEvent.DECREASE -> decreaseQuantity()
        }
    }

    fun addToCart() {
        val addedProduct = product.value?.copy(quantity = quantity.value ?: 0)
        addedProduct?.let {
            Log.d("ADDED", "$addedProduct")
            cartProductRepository.insertCartProduct(addedProduct.toEntity()) {}
        }
    }

    fun setLatestViewedProduct() {
        recentlyViewedProductRepository.getLatestViewedProduct { product ->
            _latestViewedProduct.postValue(product)
        }
    }

    private fun increaseQuantity() {
        _quantity.value = _quantity.value?.plus(1)
        setPriceSum()
    }

    private fun decreaseQuantity() {
        if ((_quantity.value ?: 0) <= 0) return
        _quantity.value = _quantity.value?.minus(1)
        setPriceSum()
    }

    private fun setPriceSum() {
        _price.value = (product.value?.price ?: 0) * (quantity.value ?: 0)
    }
}
