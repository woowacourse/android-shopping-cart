package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.ProductInCart

class ShoppingCartAdapter(
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private val items: MutableList<ProductInCart> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(onClick, ShoppingCartViewHolder.getView(parent))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun initProducts(productsInCart: List<ProductInCart>) {
        Log.d("123123", productsInCart.toString())
        items.clear()
        items.addAll(productsInCart)
        notifyDataSetChanged()
    }
}
