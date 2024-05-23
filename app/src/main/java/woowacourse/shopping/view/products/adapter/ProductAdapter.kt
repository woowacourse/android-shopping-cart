package woowacourse.shopping.view.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.products.ProductListActionHandler
import woowacourse.shopping.view.products.adapter.ProductItemType.Companion.PRODUCT_VIEW_TYPE
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder.LoadMoreViewHolder
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder.ProductViewHolder

class ProductAdapter(
    private val productListActionHandler: ProductListActionHandler,
    private val countActionHandler: CountActionHandler,
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
                    countActionHandler,
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
            is ProductItemType.ProductItem -> (holder as ProductViewHolder).bind(content.item)
            is ProductItemType.LoadMore -> (holder as LoadMoreViewHolder).bind()
        }
    }

    fun updateProducts(
        updatedProducts: List<ProductWithQuantity>,
        hasNextPage: Boolean,
    ) {
        val startPosition = productItems.size
        val newProducts = updatedProducts.drop((startPosition - 1).coerceAtLeast(0))
        val productTypes = newProducts.map { ProductItemType.ProductItem(it) }

        val beforeProductItems = productItems.dropLast(1)
        val insertedItems =
            if (hasNextPage) {
                beforeProductItems + productTypes + ProductItemType.LoadMore()
            } else {
                beforeProductItems + productTypes
            }
        this.productItems = insertedItems
        notifyItemRangeInserted(startPosition, newProducts.size)
    }

    fun updateProduct(updatedItem: ProductWithQuantity) {
        val index = findIndexOfProduct(updatedItem.product.id)
        if (index != -1) {
            productItems =
                productItems.toMutableList().apply {
                    set(index, ProductItemType.ProductItem(updatedItem))
                }
            notifyItemChanged(index)
        }
    }

    private fun findIndexOfProduct(productId: Long): Int {
        return productItems.indexOfFirst {
            if (it is ProductItemType.ProductItem) {
                it.item.product.id == productId
            } else {
                false
            }
        }
    }
}
