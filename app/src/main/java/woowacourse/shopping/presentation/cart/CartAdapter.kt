package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.BindableAdapter

class CartAdapter(
    private val cartItemDeleteClickListener: CartItemDeleteClickListener,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(), BindableAdapter<Order> {
    private var orders: List<Order> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false)
        return CartViewHolder(binding, cartItemDeleteClickListener)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(orders[position])
    }

    override fun setData(data: List<Order>) {
        val currentSize = this.orders.size
        this.orders = data
        notifyItemRangeRemoved(0, currentSize)
        notifyItemRangeInserted(0, this.orders.size)
    }

    class CartViewHolder(
        private val binding: ItemCartBinding,
        cartItemDeleteClickListener: CartItemDeleteClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cartItemDeleteClickListener = cartItemDeleteClickListener
        }
        fun bind(order: Order) {
            binding.order = order
        }
    }
}
