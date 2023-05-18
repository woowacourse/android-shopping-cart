package woowacourse.shopping.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.ShoppingProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductAdapter(
    private val onProductItemClick: (ShoppingProductModel) -> Unit,
    private val onMinusAmountButtonClick: (ShoppingProductModel) -> Unit,
    private val onPlusAmountButtonClick: (ShoppingProductModel) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val products = mutableListOf<ShoppingProductModel>()
    private val onProductItemViewClick: (Int) -> Unit = { onProductItemClick(products[it]) }
    private val onMinusAmountButtonViewClick: (Int) -> Unit = { onMinusAmountButtonClick(products[it]) }
    private val onPlusAmountButtonViewClick: (Int) -> Unit = { onPlusAmountButtonClick(products[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onProductItemViewClick,
            onMinusAmountButtonViewClick,
            onPlusAmountButtonViewClick
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    fun addProducts(products: List<ShoppingProductModel>) {
        val lastPosition = itemCount
        this.products += products
        notifyItemRangeInserted(lastPosition, products.size)
    }

    fun updateProduct(prev: ShoppingProductModel, new: ShoppingProductModel) {
        val index = products.indexOf(prev)
        products[index] = new
        notifyItemChanged(index)
    }

    fun updateProducts(products: List<ShoppingProductModel>) {
        this.products.clear()
        this.products += products
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 0
    }
}
