package woowacourse.shopping.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product

class ProductAdapter(
    private var products: List<Product>,
    private val eventListener: OnProductListener,
) : RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder = ProductViewHolder.from(parent, eventListener)

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun setItems(newItems: List<Product>) {
        products = newItems
        notifyDataSetChanged()
    }
}
