package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.feature.list.item.ProductView

class RecentProductViewHolder(binding: ViewBinding) : ItemViewHolder(binding) {
    private val binding = binding as ItemRecentBinding

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        val productItem = productView as ProductView.ProductItem

        Glide.with(binding.root.context)
            .load(productItem.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.recentImageView)

        binding.recentName.text = productItem.name
    }
}
