package woowacourse.shopping.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.list.item.ListItem

abstract class ItemHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(listItem: ListItem, onClick: (ListItem) -> Unit)
}
