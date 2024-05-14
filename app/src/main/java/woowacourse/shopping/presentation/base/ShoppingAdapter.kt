package woowacourse.shopping.presentation.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.base.ShoppingViewHolder.LoadViewHolder.Companion.LOAD_VIEW_TYPE
import woowacourse.shopping.presentation.base.ShoppingViewHolder.ProductViewHolder.Companion.PRODUCT_VIEW_TYPE
import woowacourse.shopping.presentation.ui.DisplayItem
import woowacourse.shopping.presentation.ui.LoadingItem
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

class ShoppingAdapter(
    private val shoppingHandler: ShoppingHandler,
    private var items: List<DisplayItem> = emptyList(),
) : RecyclerView.Adapter<ShoppingViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Product -> PRODUCT_VIEW_TYPE
            is LoadingItem -> LOAD_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PRODUCT_VIEW_TYPE -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.ProductViewHolder(binding, shoppingHandler)
            }

            else -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.ProductViewHolder(binding, shoppingHandler)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ShoppingViewHolder.ProductViewHolder -> {
                holder.bind(items[position] as Product)
            }

            is ShoppingViewHolder.LoadViewHolder -> {
                holder.bind(items[position] as LoadingItem)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(private val binding: ItemProductBinding, val shoppingHandler: ShoppingHandler) :
        ShoppingViewHolder(binding.root) {
        private var id: Long = -1

        init {
            binding.root.setOnClickListener {
                shoppingHandler.onClick(id)
            }
        }

        fun bind(item: Product) {
            id = item.id
            binding.tvName.text = item.name
            binding.tvPrice.text = item.price.toString()
        }

        companion object {
            const val PRODUCT_VIEW_TYPE = 0
        }
    }

    class LoadViewHolder(view: View) : ShoppingViewHolder(view) {
        fun bind(item: LoadingItem) {
        }

        companion object {
            const val LOAD_VIEW_TYPE = 1
        }
    }
}
