package woowacourse.shopping.presentation.base

import android.annotation.SuppressLint
import android.util.Log
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

class ShoppingAdapter(private var items: List<DisplayItem> = emptyList()) :
    RecyclerView.Adapter<ShoppingViewHolder>() {
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
                ShoppingViewHolder.ProductViewHolder(binding)
            }

            else -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.ProductViewHolder(binding)
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
        Log.d("테스트", "${newItems.size}")
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(private val binding: ItemProductBinding) :
        ShoppingViewHolder(binding.root) {
        fun bind(item: Product) {
            Log.d("테스트", "${item.name}")
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
