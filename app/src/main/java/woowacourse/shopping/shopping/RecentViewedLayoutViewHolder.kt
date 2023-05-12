package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentViewedLayoutBinding
import woowacourse.shopping.model.ProductUiModel

class RecentViewedLayoutViewHolder private constructor(
    private val binding: RecentViewedLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recentViewedProducts: List<ProductUiModel>) {
        binding.recyclerViewRecentViewedProduct.adapter = RecentViewedRecyclerAdapter(
            products = recentViewedProducts
        )
    }

    companion object {
        fun from(parent: ViewGroup): RecentViewedLayoutViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecentViewedLayoutBinding.inflate(layoutInflater, parent, false)

            return RecentViewedLayoutViewHolder(binding)
        }
    }
}
