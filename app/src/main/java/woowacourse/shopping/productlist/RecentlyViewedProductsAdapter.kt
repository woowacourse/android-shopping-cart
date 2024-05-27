package woowacourse.shopping.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedProductBinding
import woowacourse.shopping.uimodel.ProductUiModel

class RecentlyViewedProductsAdapter(
    private val onClickListener: RecentlyViewedProductsClickAction
) : RecyclerView.Adapter<RecentlyViewedProductsAdapter.RecentlyViewedProductsViewHolder>() {
    private var recentItems: MutableList<ProductUiModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedProductsViewHolder {
        val binding = ItemRecentlyViewedProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyViewedProductsViewHolder(binding, onClickListener)
    }

    class RecentlyViewedProductsViewHolder(
        private val binding: ItemRecentlyViewedProductBinding,
        private val onClickListener: RecentlyViewedProductsClickAction,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductUiModel) {
            binding.productUiModel = item
            binding.clickListener = onClickListener
        }
    }

    override fun getItemCount(): Int = recentItems.size

    override fun onBindViewHolder(holder: RecentlyViewedProductsViewHolder, position: Int) {
        holder.bind(recentItems[position])
    }

    fun submitRecentItems(items: List<ProductUiModel>) {
        recentItems = items.toMutableList()
        notifyItemRangeChanged(0, items.size)
    }

    fun addRecentItem(item: ProductUiModel) {
        if (recentItems.contains(item)) {
            val existPosition = recentItems.indexOf(item)
            recentItems.removeAt(existPosition)
            notifyItemRemoved(existPosition)
        }
        recentItems.add(0, item)
        notifyItemInserted(0)
        if (recentItems.size > 10) {
            recentItems.removeAt(recentItems.size - 1)
            notifyItemRemoved(recentItems.size - 1)
        }
    }
}
