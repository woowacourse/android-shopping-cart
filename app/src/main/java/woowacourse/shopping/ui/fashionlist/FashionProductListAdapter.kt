package woowacourse.shopping.ui.fashionlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentLayoutBinding
import woowacourse.shopping.databinding.LoadMoreItemBinding
import woowacourse.shopping.databinding.ProductItemBinding

class FashionProductListAdapter(
    private val viewModel: ProductListViewModel,
    private val productClickListener: ProductClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ProductListViewType> = emptyList()

    override fun getItemViewType(position: Int): Int {
        val item = viewModel.productsUiState.value?.get(position) ?: throw IllegalArgumentException("")
        return when (item) {
            is ProductListViewType.FashionProductItem -> R.layout.product_item
            ProductListViewType.LoadMore -> R.layout.load_more_item
            is ProductListViewType.RecentProducts -> R.layout.item_recent_layout
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.product_item -> {
                val binding: ProductItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.product_item, parent, false)
                FashionProductItemViewHolder(binding, viewModel, productClickListener)
            }

            R.layout.load_more_item -> {
                val binding: LoadMoreItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.load_more_item, parent, false)
                LoadMoreViewHolder(binding, viewModel)
            }

            R.layout.item_recent_layout -> {
                val binding: ItemRecentLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.item_recent_layout, parent, false)
                RecentProductLayoutViewHolder(binding)
            }

            else -> throw IllegalArgumentException("지원하지 않는 타입입니다.")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = viewModel.productsUiState.value?.get(position) ?: throw IllegalArgumentException("")
        when (holder) {
            is FashionProductItemViewHolder -> {
                holder.bind(item as ProductListViewType.FashionProductItem)
            }
            is RecentProductLayoutViewHolder -> holder.bind((item as ProductListViewType.RecentProducts).products)
        }
    }

    override fun getItemCount() = viewModel.productsUiState.value?.size ?: throw IllegalArgumentException("")

    @SuppressLint("NotifyDataSetChanged")
    fun update(uiStates: List<ProductListViewType>) {
        this.items = uiStates
        notifyDataSetChanged()
    }
}

