package woowacourse.shopping.shopping.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductAdapter(
    private var products: List<ProductModel>,
    private val onProductItemClick: (ProductModel) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val onProductItemViewClick: (Int) -> Unit = { onProductItemClick(products[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onProductItemViewClick
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun updateProducts(products: List<ProductModel>) {
        val lastPosition = itemCount
        this.products = products
        notifyItemRangeInserted(lastPosition, products.size - lastPosition)
    }

    override fun getItemViewType(position: Int): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 0
    }
}
