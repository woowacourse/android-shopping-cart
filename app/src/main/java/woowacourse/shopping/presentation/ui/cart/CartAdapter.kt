package woowacourse.shopping.presentation.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.CartProduct

class CartAdapter(
    private val cartActionHandler: CartActionHandler,
    private var carts: List<CartProduct> = emptyList(),
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding, cartActionHandler)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(carts[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<CartProduct>) {
        carts = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return carts.size
    }
}

class CartViewHolder(private val binding: ItemCartBinding, val cartActionHandler: CartActionHandler) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CartProduct) {
        binding.cart = item
        binding.cartActionHandler = cartActionHandler
    }
}
