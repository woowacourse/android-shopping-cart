package woowacourse.shopping.presentation.productlist.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.ProductViewType.RecentProductModels
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter

class RecentProductContainerViewHolder(
    private val binding: ItemRecentProductContainerBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recentProductModels: RecentProductModels, showProductDetail: (ProductModel) -> Unit) {
        binding.recyclerRecentProduct.adapter =
            RecentProductAdapter(recentProductModels.recentProducts, showProductDetail)
    }
}
