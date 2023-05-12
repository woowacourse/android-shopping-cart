package woowacourse.shopping.ui.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.viewHolder.ItemViewHolder
import woowacourse.shopping.ui.shopping.viewHolder.ProductsViewHolder
import woowacourse.shopping.ui.shopping.viewHolder.ReadMoreViewHolder
import woowacourse.shopping.ui.shopping.viewHolder.RecentViewHolder

class ProductsAdapter(
    private var datas: List<ProductsItemType>,
    private val listener: ProductsListener
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductsItemType.TYPE_HEADER -> RecentViewHolder.from(parent, listener.onClickItem)
            ProductsItemType.TYPE_ITEM -> ProductsViewHolder.from(parent) {
                listener.onClickItem((datas[it] as ProductsItemType.Product).product)
            }
            ProductsItemType.TYPE_FOOTER -> ReadMoreViewHolder.from(parent) {
                listener.onReadMoreClick()
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun getItemViewType(position: Int): Int {
        return datas[position].viewType
    }

    fun updateData(data: List<ProductsItemType>) {
        datas = data
        notifyItemChanged(0)
    }
}
