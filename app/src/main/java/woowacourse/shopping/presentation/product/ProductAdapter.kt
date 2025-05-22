package woowacourse.shopping.presentation.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.cart.CartCounterClickListener

class ProductAdapter(
    private val onClick: (CartItem) -> Unit,
    private val onClickLoadMore: () -> Unit,
    private val cartCounterClickListener: CartCounterClickListener,
    private val onAddToCartClick: (CartItem) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<CartItem> = emptyList()
    private var showLoadMore: Boolean = false

    fun setData(
        newList: List<CartItem>,
        showLoadMore: Boolean,
    ) {
//        val oldSize = items.size
//        val newSize = newList.size
//
//        val insertItemSize = newSize - oldSize
        this.items = newList
        this.showLoadMore = showLoadMore
//        if (insertItemSize > 0) {
//            notifyItemRangeInserted(oldSize, insertItemSize)
//        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_product -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ProductViewHolder(binding, onClick, cartCounterClickListener, onAddToCartClick)
            }

            R.layout.item_load_more -> {
                val binding = ItemLoadMoreBinding.inflate(inflater, parent, false)
                LoadMoreViewHolder(binding, onClickLoadMore)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(items[position])
            is LoadMoreViewHolder -> Unit
        }
    }

    override fun getItemCount(): Int = items.size + if (showLoadMore) 1 else 0

    override fun getItemViewType(position: Int): Int = if (position < items.size) R.layout.item_product else R.layout.item_load_more

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onClick: (CartItem) -> Unit,
        private val cartCounterClickListener: CartCounterClickListener,
        private val onAddToCartClick: (CartItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.cartItem = item
            binding.clProductItem.setOnClickListener { onClick(item) }

            binding.clickListener = cartCounterClickListener

            if (item.quantity > 0) {
                binding.includedLayoutCart.layoutCartQuantityBox.visibility = View.VISIBLE
                binding.ivAddCart.visibility = View.GONE
            } else {
                binding.includedLayoutCart.layoutCartQuantityBox.visibility = View.GONE
                binding.ivAddCart.visibility = View.VISIBLE
            }

            binding.ivAddCart.setOnClickListener {
                binding.includedLayoutCart.layoutCartQuantityBox.visibility = View.VISIBLE
                binding.ivAddCart.visibility = View.GONE
                onAddToCartClick(item)
            }

            binding.executePendingBindings()
        }
    }

    class LoadMoreViewHolder(
        binding: ItemLoadMoreBinding,
        onClickLoadMore: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnLoadMore.setOnClickListener { onClickLoadMore() }
        }
    }
}
