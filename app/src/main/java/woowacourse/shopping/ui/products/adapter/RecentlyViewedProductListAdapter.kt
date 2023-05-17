package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentlyViewedProductBinding
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class RecentlyViewedProductListAdapter(
    private val recentlyViewedProducts: List<RecentlyViewedProductUIState>,
    private val onClick: (Long) -> Unit,
) : RecyclerView.Adapter<RecentlyViewedProductListAdapter.RecentlyViewedProductListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedProductListViewHolder {
        return RecentlyViewedProductListViewHolder.create(parent, onClick)
    }

    override fun getItemCount(): Int = recentlyViewedProducts.size

    override fun onBindViewHolder(holder: RecentlyViewedProductListViewHolder, position: Int) {
        holder.bind(recentlyViewedProducts[position])
    }

    class RecentlyViewedProductListViewHolder private constructor(
        private val binding: ItemRecentlyViewedProductBinding,
        private val onClick: (Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recentlyViewedProduct: RecentlyViewedProductUIState) {
            binding.tvRecentlyViewedName.text = recentlyViewedProduct.name
            Glide.with(itemView)
                .load(recentlyViewedProduct.imageUrl)
                .into(binding.ivRecentlyViewed)
            binding.root.setOnClickListener { onClick(recentlyViewedProduct.productId) }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Long) -> Unit): RecentlyViewedProductListViewHolder {
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
}
