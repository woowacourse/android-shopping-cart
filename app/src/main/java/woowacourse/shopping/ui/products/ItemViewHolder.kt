package woowacourse.shopping.ui.products

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolder<ITEM : Item, BINDING : ViewDataBinding>(
    protected val binding: BINDING,
) : RecyclerView.ViewHolder(binding.root) {
    protected lateinit var item: Item

    @CallSuper
    open fun bind(item: ITEM) {
        this.item = item
    }
}
