package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.RecentProductsBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductsViewHolder(
    parent: ViewGroup,
    private val recentProducts: List<RecentProductUIModel>,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.recent_products, parent, false)
) {
    private val binding = RecentProductsBinding.bind(itemView)

    init {
        setRecentProductsVisibility()
    }

    fun bind(productOnClick: (ProductUIModel) -> Unit) {
        binding.rvRecentProductCatalogue.adapter = RecentProductsAdapter(recentProducts, productOnClick)
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
