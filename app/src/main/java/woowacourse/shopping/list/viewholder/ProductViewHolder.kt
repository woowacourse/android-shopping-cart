package woowacourse.shopping.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.model.mapper.toUi

class ProductViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemProductBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        listItem as ProductListItem

        binding.product = listItem.toUi()

        binding.root.setOnClickListener { onClick(listItem) }
    }
}
