package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productlist.viewholder.ProductListViewHolder

class ProductListAdapter(
    private val products: List<ProductModel>,
    private val onCountClick: (Long, Int) -> Unit,
    private val onItemClick: (Long) -> Unit
) : RecyclerView.Adapter<ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(parent) {
            onItemClick(products[it].id)
        }
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(products[position], onCountClick)
    }

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int = ViewType.PRODUCT_LIST.ordinal

    fun updateItemInserted(positionStart: Int, itemCount: Int) {
        notifyItemRangeInserted(positionStart, itemCount)
    }
}
