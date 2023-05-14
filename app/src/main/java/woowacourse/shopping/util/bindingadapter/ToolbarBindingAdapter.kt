package woowacourse.shopping.util.bindingadapter

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:onNavigationIconClick")
fun Toolbar.setOnNavigationIconClick(listener: OnNavigationIconClickListener) {
    setNavigationOnClickListener { listener.onClick() }
}

interface OnNavigationIconClickListener {
    fun onClick()
}
