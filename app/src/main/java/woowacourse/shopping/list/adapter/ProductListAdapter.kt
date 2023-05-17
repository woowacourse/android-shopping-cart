package woowacourse.shopping.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.list.ViewType
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.viewholder.ItemHolder
import woowacourse.shopping.list.viewholder.ProductViewHolder

class ProductListAdapter(
    private var items: List<ListItem> = listOf(),
    private val onItemClick: (ListItem) -> Unit
) : RecyclerView.Adapter<ItemHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemViewType(position: Int): Int = ViewType.PRODUCT.ordinal

    fun addItems(newItems: List<ListItem>) {
        val items = this.items.toMutableList()
        items.addAll(newItems)
        this.items = items.toList()
        notifyItemRangeInserted(items.size, newItems.size)
    }

    fun setItems(items: List<ListItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
