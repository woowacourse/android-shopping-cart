package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.view.base.QuantitySelectorEventHandler
import woowacourse.shopping.view.uimodel.ProductUiModel

interface ProductEventHandler : QuantitySelectorEventHandler {
    fun onProductSelected(productUiModel: ProductUiModel)

    fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>)

    fun whenQuantityChangedSelectView(
        view: ViewGroup,
        quantity: MutableLiveData<Int>,
    )
}
