package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentViewedLayoutBinding
import woowacourse.shopping.model.ProductUiModel

class RecentViewedLayoutViewHolder(
    private val binding: RecentViewedLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recentViewedProducts: List<ProductUiModel>) {
        val count =
            if (recentViewedProducts.size < RECENT_VIEWED_SIZE) recentViewedProducts.size else RECENT_VIEWED_SIZE
        binding.recyclerViewRecentViewedProduct.adapter = RecentViewedRecyclerAdapter(
            products = recentViewedProducts.subList(INITIAL_POSITION, count),
        )
    }

    companion object {
        private const val INITIAL_POSITION = 0
        private const val RECENT_VIEWED_SIZE = 10

        fun from(parent: ViewGroup): RecentViewedLayoutViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecentViewedLayoutBinding.inflate(layoutInflater, parent, false)

            return RecentViewedLayoutViewHolder(binding)
        }
    }
}
