package woowacourse.shopping.productcatalogue.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductCatalogueChildViewHolder(
    private val binding: ItemProductCatalogueRecentBinding,
    recentProducts: List<RecentProductUIModel>,
    productOnClick: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(recentProducts[adapterPosition].product)
        }
    }

    fun bind(recentProduct: RecentProductUIModel) {
        binding.recentProduct = recentProduct
    }
}
