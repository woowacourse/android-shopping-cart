package woowacourse.shopping.util.bindingadapter

import androidx.databinding.BindingAdapter
import woowacourse.shopping.widget.ProductCounterView

@BindingAdapter("bind:count")
fun ProductCounterView.setCount(count: Int) {
    this.count = count
}

@BindingAdapter("bind:onPlusClick")
fun ProductCounterView.setOnPlusClick(onClick: Runnable) {
    setOnPlusClickListener { _, _ -> onClick.run() }
}

@BindingAdapter("bind:onMinusClick")
fun ProductCounterView.setOnMinusClick(onClick: Runnable) {
    setOnMinusClickListener { _, _ -> onClick.run() }
}
