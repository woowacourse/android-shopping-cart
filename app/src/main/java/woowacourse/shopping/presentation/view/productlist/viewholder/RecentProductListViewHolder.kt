package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.presentation.model.RecentProductModel

class RecentProductListViewHolder(
    parent: ViewGroup,
    onProductClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recent_product_list, parent, false)
) {
    private val binding = ItemRecentProductListBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onProductClick(bindingAdapterPosition)
        }
    }

    fun bind(recentProduct: RecentProductModel) {
        Glide.with(binding.root)
            .load(recentProduct.product.imageUrl)
            .into(binding.ivRecentProductThumbnail)
        binding.tvRecentProductTitle.text = recentProduct.product.title
    }
}
