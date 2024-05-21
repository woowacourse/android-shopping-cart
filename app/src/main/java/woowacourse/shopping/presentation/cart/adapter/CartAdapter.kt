package woowacourse.shopping.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.cart.Order

class CartAdapter(
    private var orders: List<Order>,
    private val cartItemClickListener: CartItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartBinding =
            ItemCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding, cartItemClickListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is CartViewHolder -> holder.bind(orders[position])
            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    override fun getItemCount(): Int = orders.size

    fun replaceOrders(orders: List<Order>) {
        val currentSize = this.orders.size
        this.orders = orders
        notifyItemRangeRemoved(0, currentSize)
        notifyItemRangeInserted(0, this.orders.size)
    }

    companion object {
        private const val EXCEPTION_ILLEGAL_VIEW_TYPE = "유효하지 않은 뷰 타입입니다."
    }
}
