package woowacourse.shopping.feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.model.CartProductUiModel

class CartProductAdapter(
    items: List<CartProductUiModel>,
    private val cartProductClickListener: CartProductClickListener
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
        return CartProductViewHolder(binding, cartProductClickListener)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    fun setItems(newItems: List<CartProductUiModel>) {
        _items.clear()
        _items.addAll(newItems)
        notifyDataSetChanged()
    }
}
