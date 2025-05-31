package woowacourse.shopping.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.cart.CartCounterClickListener

class ProductAdapter(
    private val onClickLoadMore: () -> Unit,
    private val cartCounterClickListener: CartCounterClickListener,
    private val itemClickListener: ItemClickListener,
) : ListAdapter<ProductListItem, RecyclerView.ViewHolder>(ProductDiffCallback()) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is ProductListItem.Product -> R.layout.item_product
            is ProductListItem.LoadMore -> R.layout.item_load_more
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_product -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ProductViewHolder(binding, cartCounterClickListener, itemClickListener)
            }

            R.layout.item_load_more -> {
                val binding = ItemLoadMoreBinding.inflate(inflater, parent, false)
                LoadMoreViewHolder(binding)
            }

            else -> throw IllegalArgumentException(ERROR_INVALID_VIEW_TYPE)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is ProductListItem.Product -> (holder as ProductViewHolder).bind(item.item)
            is ProductListItem.LoadMore -> (holder as LoadMoreViewHolder).bind(onClickLoadMore)
        }
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        cartCounterClickListener: CartCounterClickListener,
        itemClickListener: ItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemClickListener = itemClickListener
            binding.counterClickListener = cartCounterClickListener
        }

        fun bind(item: CartItem) {
            binding.cartItem = item
            binding.executePendingBindings()
        }
    }

    class LoadMoreViewHolder(
        private val binding: ItemLoadMoreBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onClickLoadMore: () -> Unit) {
            binding.onClickLoadMore = onClickLoadMore
            binding.executePendingBindings()
        }
    }

    companion object {
        private const val ERROR_INVALID_VIEW_TYPE = "Invalid view type"
    }
}
