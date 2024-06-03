package woowacourse.shopping.ui.cart

import androidx.databinding.BindingAdapter

@BindingAdapter("onNavigationBackClick")
fun onNavigationBackClick(view: androidx.appcompat.widget.Toolbar, onClick: () -> Unit) {
    view.setNavigationOnClickListener { onClick() }
}