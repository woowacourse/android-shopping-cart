package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.productlist.viewholder.ProductListViewHolder

class ProductListAdapter(
    products: List<ProductModel>,
    private val onButtonClick: (ProductModel) -> Unit,
    private val onCountChanged: (Long, Int, Int) -> Unit,
) : RecyclerView.Adapter<ProductListViewHolder>() {
    private val _products = products.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            parent, { onButtonClick(_products[it]) },
            { position, count ->
                onCountChanged(_products[position].id, count, position)
                updateCartCount(position, count)
            }
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(_products[position])
    }

    override fun getItemCount(): Int = _products.size

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    fun updateDataSet(newProducts: List<ProductModel>) {
        _products.addAll(newProducts)
        notifyItemRangeInserted(itemCount, newProducts.size)
    }

    private fun updateCartCount(position: Int, count: Int) {
        _products[position] = _products[position].copy(count = count)
        notifyItemChanged(position)
    }

    fun updateProductCounts(counts: List<Int>) {
        counts.forEachIndexed { index, count ->
            _products[index] = _products[index].copy(count = count)
        }
        notifyItemRangeChanged(0, itemCount)
    }

    companion object {
        internal const val VIEW_TYPE = 100
    }
}
