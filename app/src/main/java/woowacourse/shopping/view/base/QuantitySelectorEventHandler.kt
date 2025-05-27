package woowacourse.shopping.view.base

import woowacourse.shopping.view.uimodel.QuantityObservable

interface QuantitySelectorEventHandler {
    fun onQuantityMinusSelected(uiModel: QuantityObservable)

    fun onQuantityPlusSelected(uiModel: QuantityObservable)
}
