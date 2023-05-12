package woowacourse.shopping.view.productcatalogue

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductCatalogueBinding
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductCatalogueViewHolder(
    private val binding: RecentProductCatalogueBinding,
    private val recentProducts: List<RecentProductUIModel>,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        setRecentProductsVisibility()
    }

    private fun setRecentProductsVisibility() {
        if (recentProducts.isEmpty()) {
            binding.root.layoutParams =
                RecyclerView.LayoutParams(0, 0)
            return
        }
        binding.root.layoutParams =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600)
    }
}
