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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onProductItemClick
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun updateProducts(products: List<ProductModel>) {
        this.products = products
        notifyDataSetChanged()
    }

    fun addProducts(products: List<ProductModel>) {
        val lastPosition = itemCount
        this.products += products
        notifyItemRangeInserted(lastPosition, products.size)
    }
}
