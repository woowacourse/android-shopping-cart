package woowacourse.shopping.ui.shopping.recentProductAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.recentProductAdapter.viewHolder.RecentProductViewHolder

class RecentProductsAdapter(
    private var recentProducts: List<RecentProductItem>,
    private val onClickItem: (data: ProductUIModel) -> Unit
) : RecyclerView.Adapter<RecentProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductViewHolder {
        return RecentProductViewHolder.from(parent) { onClickItem(recentProducts[it].product) }
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
}
