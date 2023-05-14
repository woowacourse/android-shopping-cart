package woowacourse.shopping.productcatalogue.recent

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductCatalogueRecentBinding
import woowacourse.shopping.uimodel.ProductUIModel

class RecentProductCatalogueChildViewHolder(
    private val binding: ItemProductCatalogueRecentBinding,
    recentProducts: RecentProductCatalogueUIModel,
    productOnClick: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(recentProducts.mainProductCatalogue.items[adapterPosition])
        }
    }

    fun bind(product: ProductUIModel) {
        binding.tvProductName.text = product.name
        Glide.with(binding.root.context)
            .load(product.url)
            .into(binding.ivProductImage)
    }
}
