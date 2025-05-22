package woowacourse.shopping.view.base

import androidx.lifecycle.MutableLiveData

interface QuantitySelectorEventHandler {
    fun onQuantityMinusSelected(quantity: MutableLiveData<Int>) {
        quantity.value = quantity.value?.minus(1)
    }

    fun onQuantityPlusSelected(quantity: MutableLiveData<Int>) {
        quantity.value = quantity.value?.plus(1)
    }
}
