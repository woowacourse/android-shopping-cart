package woowacourse.shopping.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.viewholder.RecentProductViewHolder

class RecentProductsAdapter(
    private var items: ProductView.RecentProductsItem,
) : RecyclerView.Adapter<RecentProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(items.products[position]) {}
    }

    override fun getItemCount(): Int {
        return items.products.size
    }
}
