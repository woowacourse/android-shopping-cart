package woowacourse.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductCatalogueBinding

class RecentProductCatalogueViewHolder(
    private val binding: RecentProductCatalogueBinding,
    private val recentProducts: RecentProductCatalogueUIModel,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        setRecentProductsVisibility()
    }

    private fun setRecentProductsVisibility() {
        if (recentProducts.mainProductCatalogue.items.isEmpty()) {
            binding.root.layoutParams =
                RecyclerView.LayoutParams(0, 0)
            return
        }
        binding.root.layoutParams =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600)
    }
}
