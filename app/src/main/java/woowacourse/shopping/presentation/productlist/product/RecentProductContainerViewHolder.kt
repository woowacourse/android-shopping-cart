package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductContainerBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.ProductViewType.RecentProductModels
import woowacourse.shopping.presentation.productlist.recentproduct.RecentProductAdapter

class RecentProductContainerViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_recent_product_container, parent, false),
) {
    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup) : this(parent, LayoutInflater.from(parent.context))

    private val binding = ItemRecentProductContainerBinding.bind(itemView)

    fun bind(recentProductModels: RecentProductModels, showProductDetail: (ProductModel) -> Unit) {
        binding.recyclerRecentProduct.adapter =
            RecentProductAdapter(recentProductModels.recentProducts, showProductDetail)
    }
}
