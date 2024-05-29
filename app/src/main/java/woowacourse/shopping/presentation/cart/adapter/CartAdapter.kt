package woowacourse.shopping.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.uistate.Order

class CartAdapter(
    private val cartItemClickListener: CartItemClickListener,
    private val cartItemCountHandler: CartItemCountHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var orders: MutableList<Order> = mutableListOf()
    private val ordersPosition: HashMap<Long, Int> = hashMapOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartBinding =
            ItemCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding, cartItemClickListener, cartItemCountHandler)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is CartViewHolder -> {
                val order = orders[position]
                holder.bind(order)
                ordersPosition[order.product.id] = position
            }
            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    override fun getItemCount(): Int = orders.size

    fun replaceOrders(orders: List<Order>) {
        val currentSize = this.orders.size
        this.orders = orders.toMutableList()
        notifyItemRangeRemoved(0, currentSize)
        notifyItemRangeInserted(0, this.orders.size)
    }

    fun updateOrder(order: Order) {
        val position = ordersPosition[order.product.id]
        position?.let {
            orders[position.toInt()] = order
            notifyItemChanged(position.toInt())
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
    }

    companion object {
        private const val EXCEPTION_ILLEGAL_VIEW_TYPE = "유효하지 않은 뷰 타입입니다."
    }
}
