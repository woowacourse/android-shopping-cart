package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentlyViewedProductBinding
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class RecentlyViewedProductListViewHolder private constructor(
    private val binding: ItemRecentlyViewedProductBinding,
    private val onClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick(binding.recentlyViewedProduct?.productId ?: return@setOnClickListener)
        }
    }

    fun bind(recentlyViewedProduct: RecentlyViewedProductUIState) {
        binding.recentlyViewedProduct = recentlyViewedProduct
        binding.tvRecentlyViewedName.text = recentlyViewedProduct.name
        Glide.with(itemView)
            .load(recentlyViewedProduct.imageUrl)
            .into(binding.ivRecentlyViewed)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (Long) -> Unit
        ): RecentlyViewedProductListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_recently_viewed_product,
                parent,
                false,
            )
            val binding = ItemRecentlyViewedProductBinding.bind(view)
            return RecentlyViewedProductListViewHolder(binding, onClick)
        }
    }
}