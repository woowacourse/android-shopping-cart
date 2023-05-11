package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.presentation.model.RecentProductModel

class RecentProductListViewHolder private constructor(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRecentProductListBinding.bind(view)

    constructor(view: ViewGroup, onProductClick: (Int) -> Unit) : this(
        LayoutInflater.from(view.context).inflate(
            R.layout.item_recent_product_list, view, false
        )
    ) {
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
