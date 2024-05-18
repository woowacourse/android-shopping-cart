package woowacourse.shopping.presentation.ui.shoppingcart.adapter

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderOrderBinding
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActionHandler

class OrderListAdapter(
    private val actionHandler: ShoppingCartActionHandler,
    private val orderList: MutableList<Order> = mutableListOf(),
) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {
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
        orderList.clear()
        orderList.addAll(newOrderList)

        if (orderList.size != PAGE_SIZE) {
            notifyItemRangeRemoved(
                orderList.size,
                PAGE_SIZE,
            )
        }

        notifyDataSetChanged()
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
