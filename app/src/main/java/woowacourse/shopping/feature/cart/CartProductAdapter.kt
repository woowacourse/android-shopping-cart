package woowacourse.shopping.feature.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CartProductAdapter(
    items: List<CartProductItemModel>
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder.create(parent)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    fun setItems(newItems: List<CartProductItemModel>) {
        _items.clear()
        _items.addAll(newItems)
        notifyDataSetChanged()
    }
}
