package woowacourse.shopping.feature.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.viewholder.RecentProductViewHolder

class RecentProductsAdapter(
    private var items: ProductView.RecentProductsItem,
) : RecyclerView.Adapter<RecentProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        return RecentProductViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(items.products[position]) {}
    }

    override fun getItemCount(): Int {
        return items.products.size
    }
}
