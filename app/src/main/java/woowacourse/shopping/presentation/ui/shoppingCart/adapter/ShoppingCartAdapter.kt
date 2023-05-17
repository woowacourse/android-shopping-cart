package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.ProductInCart

class ShoppingCartAdapter(
    private val onClick: (productInCart: ProductInCart) -> Unit,
    private val clickDelete: (productInCart: ProductInCart) -> Boolean,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val items: MutableList<ProductInCart> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(
            { onClick(items[it]) },
            {
                val result = clickDelete(items[it])
                if (result) deleteItem(it)
            },
            ShoppingCartViewHolder.getView(parent),
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun initProducts(productsInCart: List<ProductInCart>) {
        items.clear()
        items.addAll(productsInCart)
        notifyDataSetChanged()
    }
}
