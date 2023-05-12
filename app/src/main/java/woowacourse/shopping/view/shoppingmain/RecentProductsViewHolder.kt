package woowacourse.shopping.view.shoppingmain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductsBinding
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductsViewHolder(
    private val binding: RecentProductsBinding,
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
