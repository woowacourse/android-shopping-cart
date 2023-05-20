package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener

class ShoppingCartAdapter(
    private val onClick: ShoppingCartSetClickListener,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val items: MutableList<ProductInCartUiState> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(
            onClick,
            ShoppingCartViewHolder.getView(parent),
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun deleteItem(productId: Long) {
        val removed = items.removeAll { it.product.id == productId }
        if (removed) {
            notifyDataSetChanged()
        }
    }

    fun initProducts(productInCart: List<ProductInCartUiState>) {
        items.clear()
        items.addAll(productInCart)
        notifyDataSetChanged()
    }
}
