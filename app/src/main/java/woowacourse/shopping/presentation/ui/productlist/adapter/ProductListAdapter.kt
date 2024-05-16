package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderLoadMoreBinding
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.ProductListViewHolder.LoadMoreViewHolder
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.ProductListViewHolder.ProductViewHolder

class ProductListAdapter(
    private val actionHandler: ProductListActionHandler,
    private var pagingProduct: PagingProduct = PagingProduct(0, emptyList(), true),
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOAD_VIEW_TYPE else PRODUCT_VIEW_TYPE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            PRODUCT_VIEW_TYPE -> {
                ProductViewHolder(
                    HolderProductBinding.inflate(inflater, parent, false),
                    actionHandler,
                )
            }

            else -> {
                LoadMoreViewHolder(
                    HolderLoadMoreBinding.inflate(inflater, parent, false),
                    actionHandler,
                )
            }
        }
    }

    override fun getItemCount(): Int = pagingProduct.productList.size + 1

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(pagingProduct.productList[position])
            }

            is LoadMoreViewHolder -> {
                holder.bind(pagingProduct.last)
            }
        }
    }

    fun updateProductList(newPagingProduct: PagingProduct) {
        val positionStart = pagingProduct.productList.size
        val itemCount = newPagingProduct.productList.size - pagingProduct.productList.size

        pagingProduct = newPagingProduct
        notifyItemRangeChanged(positionStart, itemCount)
    }

    sealed class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class ProductViewHolder(
            private val binding: HolderProductBinding,
            private val actionHandler: ProductListActionHandler,
        ) : ProductListViewHolder(binding.root) {
            fun bind(product: Product) {
                binding.product = product
                binding.actionHandler = actionHandler
            }
        }

        class LoadMoreViewHolder(
            private val binding: HolderLoadMoreBinding,
            private val actionHandler: ProductListActionHandler,
        ) : ProductListViewHolder(binding.root) {
            fun bind(last: Boolean) {
                binding.last = last
                binding.actionHandler = actionHandler
            }
        }
    }

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_VIEW_TYPE = 1
    }
}
