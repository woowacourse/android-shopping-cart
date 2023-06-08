package woowacourse.shopping.feature.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.ViewType
import woowacourse.shopping.feature.cart.model.CartProductState
import woowacourse.shopping.feature.product.model.ProductState

class ProductListAdapter(
    private var productStates: List<ProductState> = listOf(),
    private var cartProductStates: List<CartProductState> = listOf(),
    private val onProductClick: (productState: ProductState) -> Unit,
    private val cartProductAddFab: (productState: ProductState) -> Unit,
    private val cartProductCountMinus: (cartProductState: CartProductState) -> Unit,
    private val cartProductCountPlus: (cartProductState: CartProductState) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun getItemCount(): Int {
        return productStates.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.createInstance(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productState: ProductState = productStates[position]
        val cartProduct: CartProductState? = cartProductStates.find { it.productId == productState.id }

        holder.bind(
            productState, cartProduct,
            onProductClick, cartProductAddFab, cartProductCountMinus, cartProductCountPlus
        )
    }

    override fun getItemViewType(position: Int): Int = ViewType.PRODUCT.ordinal

    fun addItems(newItems: List<ProductState>) {
        val items = this.productStates.toMutableList()
        items.addAll(newItems)
        this.productStates = items.toList()
        notifyItemRangeInserted(items.size, newItems.size)
    }

    fun setProducts(items: List<ProductState>) {
        this.productStates = items.toList()
        notifyDataSetChanged()
    }

    fun setCartProducts(items: List<CartProductState>) {
        this.cartProductStates = items.toList()
        notifyDataSetChanged()
    }

    fun changeCartProduct(item: CartProductState) {
        val productPosition: Int = this.productStates.indexOfFirst { it.id == item.productId }
        val cartPosition: Int = this.cartProductStates.indexOfFirst { it.productId == item.productId }
        if (cartPosition != -1) {
            this.cartProductStates.toMutableList()[cartPosition].count = item.count
            notifyItemChanged(productPosition)
        }
    }
}
