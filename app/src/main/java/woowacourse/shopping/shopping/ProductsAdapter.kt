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
    private val onClickAdd: (ProductUIModel) -> Unit,
    private val onClickPlus: (ProductUIModel) -> Unit,
    private val onClickMinus: (ProductUIModel) -> Unit,
    private val loadCartCount: (Int) -> Int,
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var productItemTypes: MutableList<ProductsItemType> = productItemTypes.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductsItemType.TYPE_HEADER -> RecentProductsViewHolder.from(parent, onClickItem)
            ProductsItemType.TYPE_FOOTER -> ReadMoreViewHolder.from(parent) { onReadMoreClick() }
            ProductsItemType.TYPE_ITEM -> ProductsViewHolder.from(
                parent,
                {
                    onClickItem((productItemTypes[it] as ProductItem).product)
                },
                { onClickAdd((productItemTypes[it] as ProductItem).product) },
                { onClickPlus((productItemTypes[it] as ProductItem).product) },
                { onClickMinus((productItemTypes[it] as ProductItem).product) },
                { loadCartCount(it) },
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

    fun updateProducts(start: Int, products: List<ProductsItemType>) {
        productItemTypes = products.toMutableList()
        notifyItemRangeChanged(start, productItemTypes.size)
    }

    fun updateRecentProducts(products: List<ProductsItemType>) {
        productItemTypes = products.toMutableList()
        notifyItemChanged(0)
    }
}
