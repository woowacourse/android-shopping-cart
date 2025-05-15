package woowacourse.shopping.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductAdapter(
    private val onClick: (Product) -> Unit,
    private val onClickLoadMore: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Product> = emptyList()
    private var showLoadMore: Boolean = false

    fun setData(
        newList: List<Product>,
        showLoadMore: Boolean,
    ) {
        val oldSize = items.size
        val newSize = newList.size

        val insertItemSize = newSize - oldSize
        this.items = newList
        this.showLoadMore = showLoadMore
        if (insertItemSize > 0) {
            notifyItemRangeInserted(oldSize, insertItemSize)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_PRODUCT) {
            val binding =
                ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            ProductViewHolder(binding, onClick)
        } else {
            val binding =
                ItemLoadMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            LoadMoreViewHolder(binding)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (holder is ProductViewHolder) {
            holder.bind(items[position])
        } else if (holder is LoadMoreViewHolder) {
            holder.bind(onClickLoadMore)
        }
    }

    override fun getItemCount(): Int = items.size + if (showLoadMore) 1 else 0

    override fun getItemViewType(position: Int): Int = if (position < items.size) VIEW_TYPE_PRODUCT else VIEW_TYPE_LOAD_MORE

    class ProductViewHolder(
        val binding: ItemProductBinding,
        val onClick: (Product) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentItem: Product? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let { onClick(it) }
            }
        }

        fun bind(item: Product) {
            binding.product = item
            currentItem = item
        }
    }

    class LoadMoreViewHolder(
        private val binding: ItemLoadMoreBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onClickLoadMore: () -> Unit) {
            binding.btnLoadMore.setOnClickListener { onClickLoadMore() }
        }
    }

    companion object {
        private const val VIEW_TYPE_PRODUCT = 0
        private const val VIEW_TYPE_LOAD_MORE = 1
    }
}
