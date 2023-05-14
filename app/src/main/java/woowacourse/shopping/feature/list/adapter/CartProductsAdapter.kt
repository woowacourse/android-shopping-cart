package woowacourse.shopping.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.viewholder.CartProductViewHolder
import woowacourse.shopping.feature.list.viewholder.ItemViewHolder

class CartProductsAdapter(
    private var items: List<ListItem> = listOf(),
    private val onDeleteItem: (ListItem) -> Unit,
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartProductBinding.inflate(inflater, parent, false)
        return CartProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], onDeleteItem)
    }

    fun setItems(items: List<ListItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
