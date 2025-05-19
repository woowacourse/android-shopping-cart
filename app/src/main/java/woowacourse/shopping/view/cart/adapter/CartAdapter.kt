package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.view.cart.vm.CartState

class CartAdapter(
    private var items: List<CartState>,
    private val handler: CartAdapterEventHandler,
) : RecyclerView.Adapter<CartViewHolder>() {
    fun submitList(newItems: List<CartState>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, handler)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
