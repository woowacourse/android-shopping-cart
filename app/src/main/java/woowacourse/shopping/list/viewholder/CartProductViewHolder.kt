package woowacourse.shopping.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.list.item.CartProductListItem
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.model.mapper.toUi

class CartProductViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemCartProductBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        listItem as CartProductListItem

        binding.cartProduct = listItem.toUi()

        binding.cartClearImageView.setOnClickListener { onClick(listItem) }
    }
}
