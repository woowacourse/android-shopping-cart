package woowacourse.shopping.productcatalogue.recent

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        binding.tvProductName.text = recentProduct.product.name
        Glide.with(binding.root.context)
            .load(recentProduct.product.imageUrl)
            .into(binding.ivProductImage)
    }
}
