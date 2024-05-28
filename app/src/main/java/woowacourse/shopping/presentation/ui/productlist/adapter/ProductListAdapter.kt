package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderLoadMoreBinding
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.common.ProductCountHandler
import woowacourse.shopping.presentation.ui.productlist.PagingProduct
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class ProductListAdapter(
    private val actionHandler: ProductListActionHandler,
    private val productCountHandler: ProductCountHandler,
) : ListAdapter<Product, ProductListAdapter.ProductListViewHolder>(ProductDiffCallback) {
    private var isLast: Boolean = false

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) LOAD_VIEW_TYPE else PRODUCT_VIEW_TYPE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            PRODUCT_VIEW_TYPE -> {
                ProductListViewHolder.ProductViewHolder(
                    HolderProductBinding.inflate(inflater, parent, false),
                    actionHandler,
                    productCountHandler,
                )
            }

            else -> {
                ProductListViewHolder.LoadMoreViewHolder(
                    HolderLoadMoreBinding.inflate(inflater, parent, false),
                    actionHandler,
                )
            }
        }
    }

    override fun getItemCount(): Int = currentList.size + 1

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductListViewHolder.ProductViewHolder -> holder.bind(getItem(position), position)
            is ProductListViewHolder.LoadMoreViewHolder -> holder.bind(isLast)
        }
    }

    fun updateProductList(newPagingProduct: PagingProduct) {
        isLast = newPagingProduct.last
        if (isLast) notifyItemChanged(currentList.size)
        submitList(newPagingProduct.productList)
    }

    sealed class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class ProductViewHolder(
            private val binding: HolderProductBinding,
            private val actionHandler: ProductListActionHandler,
            private val productCountHandler: ProductCountHandler,
        ) : ProductListViewHolder(binding.root) {
            fun bind(
                product: Product,
                position: Int,
            ) {
                binding.product = product
                binding.position = position
                binding.actionHandler = actionHandler
                binding.productCountHandler = productCountHandler
                binding.executePendingBindings()
            }
        }

        class LoadMoreViewHolder(
            private val binding: HolderLoadMoreBinding,
            private val actionHandler: ProductListActionHandler,
        ) : ProductListViewHolder(binding.root) {
            fun bind(last: Boolean) {
                binding.last = last
                binding.actionHandler = actionHandler
                binding.executePendingBindings()
            }
        }
    }

    object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product,
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_VIEW_TYPE = 1
    }
}
