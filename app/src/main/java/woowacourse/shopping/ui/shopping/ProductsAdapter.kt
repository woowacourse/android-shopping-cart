package woowacourse.shopping.ui.shopping

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.contract.ShoppingContract
import woowacourse.shopping.ui.shopping.viewHolder.ItemViewHolder
import woowacourse.shopping.ui.shopping.viewHolder.ProductsOnClickListener
import woowacourse.shopping.ui.shopping.viewHolder.ProductsViewHolder
import woowacourse.shopping.ui.shopping.viewHolder.ReadMoreViewHolder
import woowacourse.shopping.ui.shopping.viewHolder.RecentProductsViewHolder

class ProductsAdapter(
    productItemTypes: List<ProductsItemType>,
    private val presenter: ShoppingContract.Presenter,
    private val lifecycleOwner: LifecycleOwner,
    private val onClickListener: ProductsOnClickListener,
    private val onReadMoreClick: () -> Unit,
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var productItemTypes: MutableList<ProductsItemType> = productItemTypes.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductsItemType.TYPE_HEADER -> RecentProductsViewHolder.from(parent, onClickListener)
            ProductsItemType.TYPE_FOOTER -> ReadMoreViewHolder.from(parent) { onReadMoreClick() }
            ProductsItemType.TYPE_ITEM -> ProductsViewHolder.from(
                parent,
                presenter,
                lifecycleOwner,
                onClickListener,
            )
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
        notifyItemChanged(data.size - productItemTypes.size)
    }

    fun updateItemCount(id: Long, count: Int) {
        val index = productItemTypes.indexOfFirst {
            it is ProductItem && it.product.id == id
        }
        productItemTypes[index] = (productItemTypes[index] as ProductItem).copy(count = count)
    }
}
