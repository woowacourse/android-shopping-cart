package woowacourse.shopping.view.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.products.ProductListActionHandler
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder.LoadMoreViewHolder
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder.ProductViewHolder

class ProductAdapter(
    private val productListActionHandler: ProductListActionHandler,
) : RecyclerView.Adapter<ProductListViewHolder>() {
    private var products: List<Product> = emptyList()
    private var shouldShowMore: Boolean = false

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOAD_MORE_VIEW_TYPE else PRODUCT_VIEW_TYPE
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
        return products.size + 1
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(products[position])
            is LoadMoreViewHolder -> holder.bind(shouldShowMore)
        }
    }

    fun updateProducts(
        addedProducts: List<Product>,
        hasNextPage: Boolean,
    ) {
        val startPosition = products.size
        products = products + addedProducts
        shouldShowMore = hasNextPage
        notifyItemRangeInserted(startPosition, addedProducts.size)
    }

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_MORE_VIEW_TYPE = 1
    }
}
