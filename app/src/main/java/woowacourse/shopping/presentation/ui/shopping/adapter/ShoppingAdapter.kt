package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.presentation.ui.CartQuantityActionHandler
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel
import woowacourse.shopping.presentation.ui.shopping.viewholder.LoadMoreViewHolder
import woowacourse.shopping.presentation.ui.shopping.viewholder.ShoppingItem
import woowacourse.shopping.presentation.ui.shopping.viewholder.ShoppingViewHolder

class ShoppingAdapter(
    private val viewModel: ShoppingViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: MutableList<ShoppingItem> = mutableListOf()
    private val productPosition: HashMap<Long, Int> = hashMapOf()

    fun submitList(newItems: List<ProductWithQuantity>) {
        val currentSize = items.size
        val insertedItems = newItems.map { ShoppingItem.ProductType(it) }

        if (newItems.isNotEmpty()) {
            items.removeAll { it is ShoppingItem.LoadMoreType }
            val startPosition = items.size
            items.addAll(insertedItems)
            if (newItems.size >= PAGE_SIZE) {
                items.add(ShoppingItem.LoadMoreType)
            }
            newItems.forEachIndexed { index, item ->
                productPosition[item.product.id] = startPosition + index
            }
            notifyItemRangeInserted(currentSize, insertedItems.size + if (newItems.size >= PAGE_SIZE) 1 else 0)
        }
    }

    fun updateItem(newItem: ProductWithQuantity) {
        val index: Int? = productPosition[newItem.product.id]
        if (index != null) {
            items[index] = ShoppingItem.ProductType(newItem)
            notifyItemChanged(index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ShoppingItem.ProductType -> VIEW_TYPE_PRODUCT
            is ShoppingItem.LoadMoreType -> VIEW_TYPE_LOAD_MORE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PRODUCT -> {
                val binding =
                    ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ShoppingViewHolder(
                    binding,
                    viewModel as ShoppingActionHandler,
                    viewModel as CartQuantityActionHandler,
                )
            }

            VIEW_TYPE_LOAD_MORE -> {
                val binding =
                    ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadMoreViewHolder(binding, viewModel as ShoppingActionHandler)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ShoppingViewHolder -> {
                val productWithQuantity =
                    (items[position] as ShoppingItem.ProductType).productWithQuantity
                holder.bind(productWithQuantity)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isNotEmpty()) {
            when (holder) {
                is ShoppingViewHolder -> {
                    val productWithQuantity =
                        (items[position] as ShoppingItem.ProductType).productWithQuantity
                    holder.bindPartial(productWithQuantity)
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        const val VIEW_TYPE_PRODUCT = 0
        const val VIEW_TYPE_LOAD_MORE = 1
        const val PAGE_SIZE = 20
    }
}
