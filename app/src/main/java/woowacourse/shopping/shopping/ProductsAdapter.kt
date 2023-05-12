package woowacourse.shopping.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.shopping.viewHolder.ItemViewHolder
import woowacourse.shopping.shopping.viewHolder.ProductsViewHolder
import woowacourse.shopping.shopping.viewHolder.ReadMoreViewHolder
import woowacourse.shopping.shopping.viewHolder.RecentProductsViewHolder

class ProductsAdapter(
    productItemTypes: List<ProductsItemType>,
    private val onClickItem: (data: ProductUIModel) -> Unit,
    private val onReadMoreClick: () -> Unit,
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var productItemTypes: MutableList<ProductsItemType> = productItemTypes.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductsItemType.TYPE_HEADER -> RecentProductsViewHolder.from(parent, onClickItem)
            ProductsItemType.TYPE_FOOTER -> ReadMoreViewHolder.from(parent) { onReadMoreClick() }
            ProductsItemType.TYPE_ITEM -> ProductsViewHolder.from(parent) {
                onClickItem((productItemTypes[it] as ProductItem).product)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = productItemTypes.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when (holder) {
            is RecentProductsViewHolder -> holder.bind(productItemTypes[position])
            is ProductsViewHolder -> holder.bind(productItemTypes[position])
            is ReadMoreViewHolder -> return
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (productItemTypes[position]) {
            is RecentProductsItem -> ProductsItemType.TYPE_HEADER
            is ProductItem -> ProductsItemType.TYPE_ITEM
            is ProductReadMore -> ProductsItemType.TYPE_FOOTER
        }
    }

    fun updateData(data: List<ProductsItemType>) {
        productItemTypes = data.toMutableList()
        notifyDataSetChanged()
    }
}
