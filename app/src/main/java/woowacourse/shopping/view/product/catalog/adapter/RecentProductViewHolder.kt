package woowacourse.shopping.view.product.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.RecentProduct

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentProduct: RecentProduct) {
        binding.product = recentProduct.product
    }

    companion object {
        fun from(parent: ViewGroup): RecentProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
            return RecentProductViewHolder(binding)
        }
    }
}
