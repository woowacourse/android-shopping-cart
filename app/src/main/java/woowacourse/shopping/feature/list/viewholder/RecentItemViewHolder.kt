package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.item.ProductListItem

class RecentItemViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    private val binding = binding as ItemRecentBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        val productItem = listItem as ProductListItem

        Glide.with(binding.root.context)
            .load(productItem.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.recentImageView)

        binding.recentName.text = productItem.name
    }
}
