package woowacourse.shopping.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedItemViewHolder

class RecentlyViewedProductAdapter(private val clickProduct: (productId: Long) -> Unit) :
    RecyclerView.Adapter<RecentlyViewedItemViewHolder>() {
    private val items = mutableListOf<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedItemViewHolder {
        return RecentlyViewedItemViewHolder(RecentlyViewedItemViewHolder.getView(parent)) {
            clickProduct(items[it].id)
        }
    }

    override fun onBindViewHolder(holder: RecentlyViewedItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun initItems(items: List<Product>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
