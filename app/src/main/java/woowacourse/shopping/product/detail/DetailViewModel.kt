package woowacourse.shopping.product.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailViewModel(
    private val productData: ProductUiModel,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel>(productData)
    val product: LiveData<ProductUiModel> = _product

    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    private val _price = MutableLiveData<Int>(0)
    val price: LiveData<Int> = _price

    init {
        Log.d("INIT", "$productData")
    }

    fun increaseQuantity() {
        _quantity.value = _quantity.value?.plus(1)
        setPriceSum()
    }

    fun decreaseQuantity() {
        _quantity.value = _quantity.value?.minus(1)
        setPriceSum()
    }

    fun setQuantity() {
        _quantity.value = productData.quantity
        Log.d("QUANTITY", "${quantity.value}")
    }

    fun setPriceSum() {
        _price.value = (product.value?.price ?: 0) * (quantity.value ?: 0)
    }
}
