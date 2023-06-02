package woowacourse.shopping.common

import androidx.databinding.BindingAdapter
import woowacourse.shopping.productcatalogue.ProductCountClickListener
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.view.CounterView

@BindingAdapter("productCountClickListener", "cartProductUIModel")
fun setCountClickListener(
    counterView: CounterView,
    productCountClickListener: ProductCountClickListener,
    cartProductUIModel: CartProductUIModel
) {
    counterView.countUpButton.setOnClickListener {
        productCountClickListener.onUpClicked(
            cartProductUIModel,
            counterView.countTextView
        )
        counterView.count += 1
    }
    counterView.countDownButton.setOnClickListener {
        productCountClickListener.onDownClicked(
            cartProductUIModel,
            counterView.countTextView
        )
        counterView.count -= 1
    }
}
