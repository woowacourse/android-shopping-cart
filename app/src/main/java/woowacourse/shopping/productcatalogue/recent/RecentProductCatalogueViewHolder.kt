package woowacourse.shopping.productcatalogue.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductCatalogueBinding
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductCatalogueViewHolder(
    private val binding: RecentProductCatalogueBinding,
    private val recentProducts: List<RecentProductUIModel>,
) : RecyclerView.ViewHolder(binding.root) {

    fun setRecentProductsVisibility() {
        if (recentProducts.isEmpty()) {
            binding.root.layoutParams =
                RecyclerView.LayoutParams(0, 0)
            return
        }
        binding.root.layoutParams =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600)
    }
}
