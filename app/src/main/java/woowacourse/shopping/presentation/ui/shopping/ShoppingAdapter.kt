package woowacourse.shopping.presentation.ui.shopping

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.ui.DisplayItem
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewHolder.LoadViewHolder.Companion.LOAD_VIEW_TYPE
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewHolder.ProductViewHolder.Companion.PRODUCT_VIEW_TYPE

class ShoppingAdapter(
    private val shoppingHandler: ShoppingHandler,
    private var items: List<DisplayItem> = emptyList(),
) : RecyclerView.Adapter<ShoppingViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        Log.d("itemCount", "$position")
        return if (position == itemCount - 1) LOAD_VIEW_TYPE else PRODUCT_VIEW_TYPE
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
                holder.bind(items[position] as Product)
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
        return items.size + 1
    }
}

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        val shoppingHandler: ShoppingHandler,
    ) :
        ShoppingViewHolder(binding.root) {
        private var id: Long = -1

        init {
            binding.root.setOnClickListener {
                shoppingHandler.onClick(id)
            }
        }

        fun bind(item: Product) {
            Glide.with(binding.root.context)
                .load(item.imgUrl)
                .into(binding.imgItem)
            id = item.id
            Log.d("Mnaa", "${item.name}")
            binding.tvName.text = item.name
            binding.tvPrice.text = item.price.toString()
        }

        companion object {
            const val PRODUCT_VIEW_TYPE = 0
        }
    }

    class LoadViewHolder(
        private val binding: ItemLoadBinding,
        private val shoppingHandler: ShoppingHandler,
    ) : ShoppingViewHolder(binding.root) {
        fun bind() {
            binding.btnShowMore.setOnClickListener {
                shoppingHandler.loadMore()
            }
        }

        companion object {
            const val LOAD_VIEW_TYPE = 1
        }
    }
}
