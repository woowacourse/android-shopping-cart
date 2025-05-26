package woowacourse.shopping.view.product.catalog.recentproducts

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product

class RecentAdapter(
    private val onItemEventListener: OnRecentProductEventListener,
) : RecyclerView.Adapter<RecentViewHolder>() {
    private val items = mutableListOf<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecentProducts(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewHolder = RecentViewHolder.from(parent, onItemEventListener)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
