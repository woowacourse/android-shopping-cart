package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.QuantitySelectorEventHandler

interface ProductEventHandler : QuantitySelectorEventHandler {
    fun onProductSelected(product: Product)

    fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>)

    fun whenQuantityChangedSelectView(
        view: ViewGroup,
        quantity: MutableLiveData<Int>,
    )
}
