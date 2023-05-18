package woowacourse.shopping.ui.shopping.productAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.RecentProductUIModel
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.ItemViewHolder
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.ProductsViewHolder
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.ReadMoreViewHolder
import woowacourse.shopping.ui.shopping.productAdapter.viewHolder.RecentViewHolder

class ProductsAdapter(private val listener: ProductsListener) : RecyclerView.Adapter<ItemViewHolder>() {
    private val productItems: MutableList<ProductsItemType> = mutableListOf()
    private val carts: MutableList<CartProductUIModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ProductsItemType.TYPE_HEADER -> RecentViewHolder.from(parent, listener)
            ProductsItemType.TYPE_ITEM -> ProductsViewHolder.from(parent, listener)
            ProductsItemType.TYPE_FOOTER -> ReadMoreViewHolder.from(parent, listener)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(productItems[position])
    }

    override fun getItemCount(): Int {
        return productItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return productItems[position].viewType
    }

    fun submitList(
        products: List<ProductUIModel>,
        recentProducts: List<RecentProductUIModel>,
        cartProducts: List<CartProductUIModel>
    ) {
        carts.addAll(cartProducts)
        productItems.clear()
        addRecentProducts(recentProducts)
        productItems.addAll(products.map { ProductsItemType.Product(it, getCount(it.id)) })
        productItems.add(ProductsItemType.ReadMore)

        notifyItemChanged(0)
    }

    fun addList(products: List<ProductUIModel>) {
        productItems.removeIf { it is ProductsItemType.ReadMore }
        productItems.addAll(
            products.map { ProductsItemType.Product(it, getCount(it.id)) }
        )
        productItems.add(ProductsItemType.ReadMore)
        notifyItemChanged(0)
    }

    fun updateList(
        recentProducts: List<RecentProductUIModel>,
        cartProducts: List<CartProductUIModel>
    ) {
        carts.clear()
        carts.addAll(cartProducts)
        addRecentProducts(recentProducts)
        productItems.filterIsInstance<ProductsItemType.Product>()
            .forEach { it.count = getCount(it.product.id) }

        notifyItemRangeChanged(0, productItems.size - 1)
    }

    private fun addRecentProducts(recentProducts: List<RecentProductUIModel>) {
        if (productItems.size > 0 && productItems[0] is ProductsItemType.RecentProducts) {
            productItems.removeAt(0)
        }

        if (recentProducts.isNotEmpty()) {
            productItems.add(0, ProductsItemType.RecentProducts(recentProducts))
        }
    }

    private fun getCount(productId: Int): Int {
        return carts.firstOrNull { it.id == productId }?.count ?: 0
    }

    fun updateItemCount(productId: Int, count: Int) {
        val index = productItems
            .indexOfFirst { it is ProductsItemType.Product && it.product.id == productId }
        productItems[index] = (productItems[index] as ProductsItemType.Product).copy(count = count)
    }
}
