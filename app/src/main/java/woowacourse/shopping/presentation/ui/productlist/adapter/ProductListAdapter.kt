package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderHistoryListBinding
import woowacourse.shopping.databinding.HolderLoadMoreBinding
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.ProductListViewHolder.LoadMoreViewHolder
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.ProductListViewHolder.ProductViewHolder
import woowacourse.shopping.presentation.ui.productlist.uimodels.PagingProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uimodels.ProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uistates.ProductBrowsingHistoryUiState

class ProductListAdapter(
    private val actionHandler: ProductListActionHandler,
    private var pagingProductUiModel: PagingProductUiModel =
        PagingProductUiModel(
            0,
            emptyList(),
            true,
        ),
    private var historyUiState: ProductBrowsingHistoryUiState = ProductBrowsingHistoryUiState.Loading,
    private val historyListAdapter: ProductBrowsingHistoryListAdapter,
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            (position == 0) -> ProductListViewType.ProductBrowsingHistory.ordinal
            (position == itemCount - 1) -> ProductListViewType.LoadMore.ordinal
            else -> ProductListViewType.Product.ordinal
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (ProductListViewType.entries[viewType]) {
            ProductListViewType.ProductBrowsingHistory -> {
                ProductListViewHolder.HistoryViewHolder(
                    HolderHistoryListBinding.inflate(inflater, parent, false),
                    historyUiState,
                )
            }

            ProductListViewType.Product -> {
                ProductViewHolder(
                    HolderProductBinding.inflate(inflater, parent, false),
                    actionHandler,
                )
            }

            ProductListViewType.LoadMore -> {
                LoadMoreViewHolder(
                    HolderLoadMoreBinding.inflate(inflater, parent, false),
                    actionHandler,
                )
            }
        }
    }

    override fun getItemCount(): Int = pagingProductUiModel.productUiModels.size + ProductListViewType.entries.size - 1

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(pagingProductUiModel.productUiModels[position - 1])
            }

            is LoadMoreViewHolder -> {
                holder.bind(pagingProductUiModel.isLastPage)
            }

            is ProductListViewHolder.HistoryViewHolder -> {
                holder.bind(historyListAdapter)
            }
        }
    }

    fun updateProductList(newPagingProductUiModel: PagingProductUiModel) {
        val positionStart = pagingProductUiModel.productUiModels.size
        val positionEnd = newPagingProductUiModel.productUiModels.size
        val itemCount = positionEnd - positionStart

        val diff =
            newPagingProductUiModel.productUiModels - pagingProductUiModel.productUiModels.toSet()
        pagingProductUiModel = newPagingProductUiModel
        notifyItemRangeInserted(positionStart, itemCount + 1)

        newPagingProductUiModel.productUiModels.forEachIndexed { index, productUiModel ->
            if (diff.map { it.product.id }.contains(productUiModel.product.id)) {
                notifyItemChanged(index + 1)
            }
        }
    }

    fun updateHistoryListState(newHistoryUiState: ProductBrowsingHistoryUiState) {
        historyUiState = newHistoryUiState
        if (newHistoryUiState is ProductBrowsingHistoryUiState.Success) {
            historyListAdapter.submitList(newHistoryUiState.histories)
        }
        notifyItemChanged(0)
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

        class HistoryViewHolder(
            private val binding: HolderHistoryListBinding,
            private val productBrowsingHistoryUiState: ProductBrowsingHistoryUiState,
        ) : ProductListViewHolder(binding.root) {
            fun bind(historyListAdapter: ProductBrowsingHistoryListAdapter) {
                binding.rvHistoryList.adapter = historyListAdapter
                binding.state = productBrowsingHistoryUiState
            }
        }
    }
}
