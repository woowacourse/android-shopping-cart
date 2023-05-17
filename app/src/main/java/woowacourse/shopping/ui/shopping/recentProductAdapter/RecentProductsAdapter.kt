package woowacourse.shopping.ui.shopping.recentProductAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.recentProductAdapter.viewHolder.RecentProductViewHolder

class RecentProductsAdapter(
    private val recentProducts: MutableList<RecentProductItem>,
    private val onClickListener: RecentProductsListener
) : RecyclerView.Adapter<RecentProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        return RecentProductViewHolder.from(parent, onClickListener)
    }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(recentProducts[position])
    }

    override fun getItemCount(): Int {
        return recentProducts.size
    }

    override fun getItemViewType(position: Int): Int {
        return ProductsItemType.TYPE_ITEM
    }

    fun submitList(data: List<RecentProductItem>) {
        recentProducts.clear()
        recentProducts.addAll(data)
        notifyItemRangeChanged(0, data.size)
    }
}
