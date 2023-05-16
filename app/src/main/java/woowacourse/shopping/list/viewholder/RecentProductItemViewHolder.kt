package woowacourse.shopping.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.RecentProductListItem

class RecentProductItemViewHolder(val binding: ViewBinding) : ItemHolder(binding) {

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        binding as ItemRecentBinding
        listItem as RecentProductListItem

        Glide.with(binding.root.context)
            .load(listItem.productImageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.recentImageView)

        binding.recentName.text = listItem.productName
    }
}
