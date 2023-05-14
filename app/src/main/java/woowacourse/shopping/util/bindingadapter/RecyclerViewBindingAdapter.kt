package woowacourse.shopping.util.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:hasFixedSize")
fun RecyclerView.setHasFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}
