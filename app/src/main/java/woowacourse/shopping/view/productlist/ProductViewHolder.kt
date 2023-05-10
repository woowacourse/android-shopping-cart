package woowacourse.shopping.view.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentViewedBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter
import woowacourse.shopping.view.productlist.recentviewed.RecentViewedAdapter

sealed class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class RecentViewedViewHolder(private val binding: ItemRecentViewedBinding) : ProductViewHolder(binding.root) {
        fun bind(recentViewedProducts: List<ProductModel>?, onItemClick: ProductListAdapter.OnItemClick) {
            binding.recyclerRecentViewed.adapter = RecentViewedAdapter(recentViewedProducts ?: emptyList(), onItemClick)
        }
    }

    class ProductItemViewHolder(private val binding: ItemProductBinding) : ProductViewHolder(binding.root) {
        fun bind(product: ProductModel, onClick: ProductListAdapter.OnItemClick) {
            binding.product = product
            binding.onItemClick = onClick
            binding.textPrice.text = binding.root.context.getString(R.string.korean_won, PriceFormatter.format(product.price))
            Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
        }
    }

    companion object {
        fun of(parent: ViewGroup, type: ItemViewType): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                ItemViewType.RECENT_VIEWED_ITEM -> RecentViewedViewHolder(ItemRecentViewedBinding.bind(view))
                ItemViewType.PRODUCT_ITEM -> ProductItemViewHolder(ItemProductBinding.bind(view))
            }
        }
    }
}
