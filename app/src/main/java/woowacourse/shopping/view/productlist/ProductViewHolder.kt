package woowacourse.shopping.view.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentViewedBinding
import woowacourse.shopping.databinding.ItemShowMoreBinding
import woowacourse.shopping.view.productlist.recentviewed.RecentViewedAdapter

sealed class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class RecentViewedViewHolder(private val binding: ItemRecentViewedBinding) :
        ProductViewHolder(binding.root) {
        fun bind(
            item: ProductListViewItem.RecentViewedItem,
            onItemClick: ProductListAdapter.OnItemClick,
        ) {
            binding.recyclerRecentViewed.adapter =
                RecentViewedAdapter(item.products, onItemClick)
        }
    }

    class ProductItemViewHolder(
        private val binding: ItemProductBinding,
        private val onItemClick: ProductListAdapter.OnItemClick,
    ) : ProductViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
            val onPlusClick = { id: Int ->
                onItemClick.onProductUpdateCount(id, Integer.parseInt(binding.textCount.text.toString()) + 1)
                binding.textCount.text = (Integer.parseInt(binding.textCount.text.toString()) + 1).toString()
            }
            val onMinusClick = { id: Int ->
                onItemClick.onProductUpdateCount(id, Integer.parseInt(binding.textCount.text.toString()) - 1)
                binding.textCount.text = (Integer.parseInt(binding.textCount.text.toString()) - 1).toString()
            }
            binding.onPlusClick = onPlusClick
            binding.onMinusClick = onMinusClick
        }

        fun bind(item: ProductListViewItem.ProductItem) {
            binding.product = item.product
            Glide.with(binding.root.context).load(item.product.imageUrl).into(binding.imgProduct)
        }
    }

    class ShowMoreViewHolder(
        binding: ItemShowMoreBinding,
        onItemClick: ProductListAdapter.OnItemClick,
    ) : ProductViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
        }
    }

    companion object {
        fun of(
            parent: ViewGroup,
            type: ProductListViewType,
            onItemClick: ProductListAdapter.OnItemClick,
        ): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                ProductListViewType.RECENT_VIEWED_ITEM -> RecentViewedViewHolder(
                    ItemRecentViewedBinding.bind(view),
                )
                ProductListViewType.PRODUCT_ITEM -> ProductItemViewHolder(
                    ItemProductBinding.bind(
                        view,
                    ),
                    onItemClick,
                )
                ProductListViewType.SHOW_MORE_ITEM -> ShowMoreViewHolder(
                    ItemShowMoreBinding.bind(
                        view,
                    ),
                    onItemClick,
                )
            }
        }
    }
}
