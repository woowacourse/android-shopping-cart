package woowacourse.shopping.presentation.productlist.recentproduct

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class RecentProductItemViewHolder(
    private val binding: ItemRecentProductBinding,
    private val showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var productModel: ProductModel

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    fun bind(recentProduct: ProductModel) {
        productModel = recentProduct
        binding.productModel = recentProduct
    }
}
