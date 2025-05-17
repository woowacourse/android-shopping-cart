package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.products.Product

class ProductsAdapter(
    private val products: MutableList<Product> = mutableListOf(),
    private val productClickListener: (Product) -> Unit,
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val productBinding =
            ItemProductBinding.inflate(
                inflater,
                parent,
                false,
            )

        return ProductViewHolder(productBinding, productClickListener)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProductsView(list: List<Product>) {
        products.clear()
        products.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
