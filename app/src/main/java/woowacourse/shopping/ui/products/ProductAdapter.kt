package woowacourse.shopping.ui.products

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product

@SuppressLint("NotifyDataSetChanged")
class ProductAdapter(
    private val onClickHandler: OnClickHandler,
) : RecyclerView.Adapter<ItemViewHolder<Item, ViewDataBinding>>() {
    private val items: MutableList<Item> = mutableListOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemViewHolder<Item, ViewDataBinding> =
        when (ItemViewType.entries[viewType]) {
            ItemViewType.PRODUCT -> ProductViewHolder(parent, onClickHandler)
            ItemViewType.LOAD_MORE -> LoadMoreViewHolder(parent, onClickHandler)
        } as ItemViewHolder<Item, ViewDataBinding>

    override fun onBindViewHolder(
        holder: ItemViewHolder<Item, ViewDataBinding>,
        position: Int,
    ) {
        val item: Item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    fun updateProductItems(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems.map { Item.ProductItem(it) })
        notifyDataSetChanged()
    }

    fun updateLoadMoreItem(isLoadable: Boolean) {
        if (isLoadable) items.add(Item.LoadMoreItem)
        notifyDataSetChanged()
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}
