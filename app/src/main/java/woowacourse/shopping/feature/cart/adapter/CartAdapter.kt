package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Goods

class CartAdapter(
    private val cartClickListener: CartViewHolder.CartClickListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val items: MutableList<Goods> = mutableListOf()

    fun setItems(newItems: List<Goods>) {
        items.clear()
        items.addAll(newItems)
        notifyItemRangeChanged(ITEM_START_POSITION, ITEM_END_POSITION)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        binding.cartClickListener = cartClickListener
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        val item: Goods = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    companion object {
        private const val ITEM_START_POSITION = 0
        private const val ITEM_END_POSITION = 5
    }
}
