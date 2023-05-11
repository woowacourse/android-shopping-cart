package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.RecentProductModel
import woowacourse.shopping.presentation.view.productlist.viewholder.RecentProductListViewHolder

class RecentProductListAdapter(
    private val items: List<RecentProductModel>,
    private val onProductClick: (Long) -> Unit
) : RecyclerView.Adapter<RecentProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductListViewHolder {
        return RecentProductListViewHolder(parent) {
            onProductClick(items[it].product.id)
        }
    }

    override fun onBindViewHolder(holder: RecentProductListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
