package woowacourse.shopping.presentation.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods

class ShoppingCartAdapter(
    private val clickListener: ShoppingCartClickListener,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var items: List<Goods> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ShoppingCartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(goods: List<Goods>) {
        items = goods
        notifyDataSetChanged()
    }
}
