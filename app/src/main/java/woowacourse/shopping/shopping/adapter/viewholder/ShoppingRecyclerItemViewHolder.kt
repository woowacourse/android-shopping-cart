package woowacourse.shopping.shopping.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ShoppingRecyclerItemViewHolder<T : ShoppingRecyclerItem, VB : ViewDataBinding>(protected val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(itemData: T)
}
