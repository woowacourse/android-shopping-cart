package woowacourse.shopping.shopping

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.databinding.ItemRecentProductListBinding

class RecentProductViewHolder(
    private val binding: ItemRecentProductListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProductModel) {
        Glide.with(binding.root.context)
            .load(recentProduct.product.picture)
            .into(binding.recentProductListPicture)
        binding.recentProductListTitle.text = recentProduct.product.title
    }
}
