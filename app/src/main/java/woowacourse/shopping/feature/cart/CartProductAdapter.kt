package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartProductAdapter(
    items: List<CartProductItemModel>
) : RecyclerView.Adapter<CartProductViewHolder>() {
    private val _items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCartProductBinding>(
            layoutInflater,
            R.layout.item_cart_product,
            parent,
            false
        )
        return CartProductViewHolder(binding)
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
