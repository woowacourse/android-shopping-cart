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
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recently_viewed_product,
            parent,
            false,
        )

        return RecentlyViewedProductListViewHolder(
            ItemRecentlyViewedProductBinding.bind(view),
            onClick,
        )
    }

    override fun getItemCount(): Int = recentlyViewedProducts.size

    override fun onBindViewHolder(holder: RecentlyViewedProductListViewHolder, position: Int) {
        holder.bind(recentlyViewedProducts[position])
    }

    class RecentlyViewedProductListViewHolder(
        private val binding: ItemRecentlyViewedProductBinding,
        onClick: (Long) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onClick
        }

        fun bind(recentlyViewedProduct: RecentlyViewedProductUIState) {
            binding.item = recentlyViewedProduct
            binding.tvRecentlyViewedName.text = recentlyViewedProduct.name
            Glide.with(itemView)
                .load(recentlyViewedProduct.imageUrl)
                .into(binding.ivRecentlyViewed)
        }
    }
}
