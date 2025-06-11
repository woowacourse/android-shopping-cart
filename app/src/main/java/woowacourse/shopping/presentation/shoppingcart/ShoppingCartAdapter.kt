package woowacourse.shopping.presentation.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.shoppingcart.ShoppingCartItem
import woowacourse.shopping.presentation.util.QuantityClickListener

class ShoppingCartAdapter(
    private val quantityClickListener: QuantityClickListener,
    private val clickListener: ShoppingCartClickListener,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var items: List<ShoppingCartItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, quantityClickListener, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<ShoppingCartItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
