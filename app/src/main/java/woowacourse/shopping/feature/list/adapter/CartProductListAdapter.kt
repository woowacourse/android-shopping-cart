package woowacourse.shopping.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.viewholder.CartProductViewHolder
import woowacourse.shopping.feature.list.viewholder.ItemHolder

class CartProductListAdapter(
    private var items: List<ListItem> = listOf(),
    private val onXClick: (ListItem) -> Unit
) : RecyclerView.Adapter<ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartProductBinding.inflate(inflater, parent, false)
        return CartProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position], onXClick)
    }

    fun setItems(items: List<ListItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
