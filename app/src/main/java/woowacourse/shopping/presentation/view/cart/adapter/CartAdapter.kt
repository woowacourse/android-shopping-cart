package woowacourse.shopping.presentation.view.cart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class CartAdapter(
    products: List<ProductUiModel> = emptyList(),
    private val eventListener: CartEventListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, eventListener)

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProducts(products: List<ProductUiModel>) {
        this.products.clear()
        this.products.addAll(products)

        notifyDataSetChanged()
    }

    fun removeProduct(id: Long) {
        val index = products.indexOfFirst { it.id == id }
        products.removeAt(index)
        notifyItemRemoved(index)
    }

    interface CartEventListener {
        fun onProductDeletion(product: ProductUiModel)
    }
}
