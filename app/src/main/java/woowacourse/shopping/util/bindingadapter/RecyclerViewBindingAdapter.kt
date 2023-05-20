package woowacourse.shopping.util.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import woowacourse.shopping.ui.shopping.recyclerview.listener.EndScrollListener

@BindingAdapter("bind:adapter", "bind:onAdapted", requireAll = false)
fun RecyclerView.setAdapter(adapter: ConcatAdapter, onAdapted: () -> Unit) {
    this.adapter = adapter
    onAdapted.invoke()
}

@BindingAdapter("bind:fixedSize")
fun RecyclerView.setFixedSize(fixedSize: Boolean) {
    setHasFixedSize(fixedSize)
}

@BindingAdapter("bind:onEndScroll")
fun RecyclerView.setOnEndScroll(onEndScroll: () -> Unit) {
    addOnScrollListener(EndScrollListener(onEndScroll))
}

@BindingAdapter("bind:layoutManager")
fun RecyclerView.setLayoutManager(layoutManager: LayoutManager) {
    this.layoutManager = layoutManager
}
