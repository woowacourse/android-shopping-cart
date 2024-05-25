package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderLoadMoreBinding
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.ProductListViewHolder.LoadMoreViewHolder
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.ProductListViewHolder.ProductViewHolder
import woowacourse.shopping.presentation.ui.productlist.uimodels.PagingProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uimodels.ProductUiModel

class ProductListAdapter(
    private val actionHandler: ProductListActionHandler,
    private var pagingProductUiModel: PagingProductUiModel =
        PagingProductUiModel(
            0,
            emptyList(),
            true,
        ),
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

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

    override fun getItemCount(): Int = pagingProductUiModel.productUiModels.size + 1

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(pagingProductUiModel.productUiModels[position])
            }

            is LoadMoreViewHolder -> {
                holder.bind(pagingProductUiModel.isLastPage)
            }
        }
    }

    fun updateProductList(newPagingProductUiModel: PagingProductUiModel) {
        val positionStart = pagingProductUiModel.productUiModels.size
        val positionEnd = newPagingProductUiModel.productUiModels.size
        val itemCount = positionEnd - positionStart
        notifyItemRangeInserted(positionStart, itemCount)

        val diff =
            newPagingProductUiModel.productUiModels - pagingProductUiModel.productUiModels.toSet()
        pagingProductUiModel = newPagingProductUiModel
        newPagingProductUiModel.productUiModels.forEachIndexed { index, productUiModel ->
            if (diff.map { it.product.id }.contains(productUiModel.product.id)) {
                notifyItemChanged(index)
            }
        }
    }

    sealed class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class ProductViewHolder(
            private val binding: HolderProductBinding,
            private val actionHandler: ProductListActionHandler,
        ) : ProductListViewHolder(binding.root) {
            fun bind(productUiModel: ProductUiModel) {
                binding.productUiModel = productUiModel
                binding.actionHandler = actionHandler
            }
        }

        class LoadMoreViewHolder(
            private val binding: HolderLoadMoreBinding,
            private val actionHandler: ProductListActionHandler,
        ) : ProductListViewHolder(binding.root) {
            fun bind(isLastPage: Boolean) {
                binding.isLastPage = isLastPage
                binding.actionHandler = actionHandler
            }
        }
    }

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_VIEW_TYPE = 1
    }
}
