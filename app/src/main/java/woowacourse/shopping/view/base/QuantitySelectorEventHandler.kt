package woowacourse.shopping.view.base

import androidx.lifecycle.MutableLiveData

interface QuantitySelectorEventHandler {
    fun onQuantityMinusSelected(quantity: MutableLiveData<Int>)

    fun onQuantityPlusSelected(quantity: MutableLiveData<Int>)
}
