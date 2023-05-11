package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.feature.list.item.ListItem

class ProductMoreViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemMoreBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        binding.moreButton.setOnClickListener { onClick(listItem) }
    }
}
