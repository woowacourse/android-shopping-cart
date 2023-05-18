package woowacourse.shopping.productcatalogue.recent

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductCatalogueChildViewHolder(
    private val binding: ItemProductCatalogueRecentBinding,
    productOnClick: ProductClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = productOnClick
    }

    fun bind(recentProduct: RecentProductUIModel) {
        binding.recentProduct = recentProduct
    }
}
