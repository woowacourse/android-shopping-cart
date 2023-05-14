package woowacourse.shopping.ui.shopping.productAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.ItemViewHolder
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.ProductsViewHolder
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.ReadMoreViewHolder
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.RecentViewHolder

class ProductsAdapter(
    private var products: List<ProductsItemType>,
    private val listener: ProductsListener
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductsItemType.TYPE_HEADER -> RecentViewHolder.from(parent, listener)
            ProductsItemType.TYPE_ITEM -> ProductsViewHolder.from(parent, listener)
            ProductsItemType.TYPE_FOOTER -> ReadMoreViewHolder.from(parent, listener)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun getItemViewType(position: Int): Int {
        return products[position].viewType
    }

    fun submitList(data: List<ProductsItemType>) {
        products = data
        notifyItemChanged(products.size - data.size)
    }
}
