package woowacourse.shopping.view.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.ProductListActionHandler
import woowacourse.shopping.view.products.adapter.ProductItemType.Companion.PRODUCT_VIEW_TYPE
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder.LoadMoreViewHolder
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder.ProductViewHolder

class ProductAdapter(
    private val productListActionHandler: ProductListActionHandler,
) : RecyclerView.Adapter<ProductListViewHolder>() {
    private var productItems: List<ProductItemType> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return productItems[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductListViewHolder {
        return when (viewType) {
            PRODUCT_VIEW_TYPE ->
                ProductViewHolder(
                    ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    productListActionHandler,
                )
            else ->
                LoadMoreViewHolder(
                    ItemMoreLoadBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    productListActionHandler,
                )
        }
    }

    override fun getItemCount(): Int {
        return productItems.size
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        when (val content = productItems[position]) {
            is ProductItemType.ProductItem -> (holder as ProductViewHolder).bind(content.product)
            is ProductItemType.LoadMore -> (holder as LoadMoreViewHolder).bind()
        }
    }

    fun updateProducts(
        newProducts: List<Product>,
        hasNextPage: Boolean,
    ) {
        val startPosition = productItems.size
        val productTypes = newProducts.map { ProductItemType.ProductItem(it) }
        val insertedItems =
            if (hasNextPage) {
                productTypes + ProductItemType.LoadMore()
            } else {
                productTypes
            }
        val newItemsSize = insertedItems.size - startPosition
        this.productItems = insertedItems
        notifyItemRangeInserted(startPosition, newItemsSize)
    }
}
