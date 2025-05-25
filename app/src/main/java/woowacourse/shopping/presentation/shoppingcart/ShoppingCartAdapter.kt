package woowacourse.shopping.presentation.shoppingcart

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.QuantitySelectorListener

class ShoppingCartAdapter(
    private val shoppingCartClickListener: ShoppingCartClickListener,
    private val quantitySelectorListener: QuantitySelectorListener,
) : RecyclerView.Adapter<ShoppingCartViewHolder>() {
    private var items: List<Goods> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewHolder {
        return ShoppingCartViewHolder(parent, shoppingCartClickListener, quantitySelectorListener)
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

    fun changeQuantity(goodsId: Int) {
        val position = items.indexOfFirst { it.id == goodsId }
        notifyItemChanged(position)
    }
}
