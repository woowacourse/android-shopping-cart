package woowacourse.shopping.view.shoppingmain

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductRecentBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductViewHolder(
    private val binding: ItemProductRecentBinding,
    recentProducts: List<RecentProductUIModel>,
    productOnClick: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            productOnClick(recentProducts[adapterPosition].productUIModel)
        }
    }

    fun bind(product: ProductUIModel) {
        binding.tvProductName.text = product.name
        Glide.with(binding.root.context)
            .load(product.url)
            .into(binding.ivProductImage)
    }
}
