package woowacourse.shopping.presentation.ui.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderOrderBinding
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActionHandler
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartViewModel

class OrderListAdapter(
    private val actionHandler: ShoppingCartActionHandler,
    private var orderList: List<Order> = emptyList(),
) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding, actionHandler)
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int,
    ) {
        holder.bind(orderList[position])
    }

    fun updateOrderList(newOrderList: List<Order>) {
        orderList = newOrderList

        if (orderList.size != ShoppingCartViewModel.PAGE_SIZE) {
            notifyItemRangeRemoved(
                orderList.size + 1,
                orderList.size + 1 + ShoppingCartViewModel.PAGE_SIZE,
            )
        }

        notifyItemRangeChanged(0, orderList.size)
    }

    class OrderViewHolder(
        private val binding: HolderOrderBinding,
        private val actionHandler: ShoppingCartActionHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order = order
            binding.actionHandler = actionHandler
        }
    }
}
