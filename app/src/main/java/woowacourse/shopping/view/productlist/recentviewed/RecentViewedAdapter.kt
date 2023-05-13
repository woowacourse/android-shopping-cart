package woowacourse.shopping.view.productlist.recentviewed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.productlist.ProductListAdapter

class RecentViewedAdapter(
    private val products: List<ProductModel>,
    private val onItemClick: ProductListAdapter.OnItemClick,
) : RecyclerView.Adapter<RecentViewedItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewedItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_viewed_product, parent, false)
        return RecentViewedItemViewHolder(ItemRecentViewedProductBinding.bind(view))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecentViewedItemViewHolder, position: Int) {
        holder.bind(products[position], onItemClick)
    }
}
