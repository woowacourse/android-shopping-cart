package woowacourse.shopping.view.main.adapter

import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.QuantitySelectorEventHandler

interface ProductEventHandler : QuantitySelectorEventHandler {
    fun onProductSelected(product: Product)

    fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>)

    fun whenQuantityChangedSelectView(quantity: MutableLiveData<Int>)
}
