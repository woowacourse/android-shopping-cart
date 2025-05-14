package woowacourse.shopping.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.product.catalog.ProductUiModel

class CartAdapter(
    private val cartProducts: List<ProductUiModel>,
    private val onDeleteProductClick: DeleteProductClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder = CartViewHolder.from(parent, onDeleteProductClick)

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(cartProducts[position])
    }

    override fun getItemCount(): Int = cartProducts.size
}
