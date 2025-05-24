package woowacourse.shopping.view.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductCountViewModel : ViewModel() {
    private val _productCount = MutableLiveData<Int>(1)
    val productCount: LiveData<Int> get() = _productCount

    private val _isMinimumProductCount = MutableLiveData<Boolean>(false)
    val isMinimumProductCount: LiveData<Boolean> get() = _isMinimumProductCount

    fun increase() {
        _productCount.let { it.postValue(it.value?.plus(1)) }
    }

    fun decrease() {
        val currentProductCount = _productCount.value ?: 1
        if (currentProductCount == 1) {
            _isMinimumProductCount.value = true
        } else {
            _isMinimumProductCount.value = false
            _productCount.let { it.postValue(it.value?.minus(1)) }
        }
    }
}
