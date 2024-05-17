package woowacourse.shopping.presentation.ui.shopping.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

class ShoppingAdapter(
    private val shoppingHandler: ShoppingHandler,
    private var items: List<Product> = emptyList(),
) : RecyclerView.Adapter<ShoppingViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - LOAD_MORE_COUNT) ShoppingViewType.LoadMore.value else ShoppingViewType.Product.value
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ShoppingViewType.of(viewType)) {
            ShoppingViewType.Product -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.ProductViewHolder(binding, shoppingHandler)
            }

            ShoppingViewType.LoadMore -> {
                val binding = ItemLoadBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.LoadViewHolder(binding, shoppingHandler)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ShoppingViewHolder.ProductViewHolder -> {
                holder.bind(items[position])
            }

            is ShoppingViewHolder.LoadViewHolder -> {
                holder.bind()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size + LOAD_MORE_COUNT
    }

    companion object {
        private const val LOAD_MORE_COUNT = 1
    }
}
