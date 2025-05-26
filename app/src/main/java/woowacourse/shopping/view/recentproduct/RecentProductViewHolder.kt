package woowacourse.shopping.view.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.product.Product

class RecentProductViewHolder(
    private val binding: ItemRecentProductBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(parent: ViewGroup): RecentProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
            return RecentProductViewHolder(binding)
        }
    }
}
