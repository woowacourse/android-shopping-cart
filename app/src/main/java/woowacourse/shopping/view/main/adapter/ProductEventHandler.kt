package woowacourse.shopping.view.main.adapter

import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.Product

interface ProductEventHandler {
    fun onProductSelected(product: Product)

    fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>)

    fun onQuantityMinusSelected(quantity: MutableLiveData<Int>)

    fun onQuantityPlusSelected(quantity: MutableLiveData<Int>)

    fun whenQuantityChangedSelectView(quantity: MutableLiveData<Int>)
}
