package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentlyViewedProductItemBinding

class RecentlyViewedProductViewHolder(
    private val binding: RecentlyViewedProductItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            recentlyViewedProductClickListener: RecentlyViewedProductClickListener,
        ): RecentlyViewedProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecentlyViewedProductItemBinding.inflate(inflater, parent, false)
            binding.clickListener = recentlyViewedProductClickListener
            return RecentlyViewedProductViewHolder(binding)
        }
    }
}
