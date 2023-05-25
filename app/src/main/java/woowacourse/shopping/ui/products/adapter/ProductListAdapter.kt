package woowacourse.shopping.ui.products.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.products.uistate.ProductUIState

class ProductListAdapter(
    private val products: MutableList<ProductUIState>,
    private val onClick: (Long) -> Unit,
    private val onClickAddToCartButton: (Long) -> Unit,
    private val onClickPlusCount: (Long) -> Unit,
    private val onClickMinusCount: (Long) -> Unit
) : RecyclerView.Adapter<ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder.create(
            parent,
            onClick,
            onClickAddToCartButton,
            onClickPlusCount,
            onClickMinusCount
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(products[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newProducts: List<ProductUIState>) {
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    fun replaceItem(newProduct: ProductUIState) {
        val index = products.indexOfFirst { newProduct.id == it.id }
        if (index == -1) return
        products[index] = newProduct
        notifyItemChanged(index)
    }
}
