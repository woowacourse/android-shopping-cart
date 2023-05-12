package woowacourse.shopping.view.productlist.recentviewed

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productlist.ProductListAdapter

class RecentViewedItemViewHolder(private val binding: ItemRecentViewedProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel, onItemClick: ProductListAdapter.OnItemClick) {
        binding.product = product
        Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
        binding.onItemClick = onItemClick
    }
}
