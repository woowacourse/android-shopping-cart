package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemInventoryProductBinding
import woowacourse.shopping.databinding.ItemInventoryRecentListBinding
import woowacourse.shopping.databinding.ItemInventoryShowMoreBinding
import woowacourse.shopping.view.inventory.InventoryEventHandler
import woowacourse.shopping.view.inventory.RecentListAdapter
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.item.InventoryItem.RecentProducts
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore

sealed class InventoryViewHolder<BINDING : ViewBinding>(protected val binding: BINDING) :
    RecyclerView.ViewHolder(binding.root) {
    class RecentItemsListViewHolder(
        parent: ViewGroup,
        handler: InventoryEventHandler,
    ) :
        InventoryViewHolder<ItemInventoryRecentListBinding>(
                ItemInventoryRecentListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            ) {
        private val adapter = RecentListAdapter(handler)

        init {
            binding.rvRecentList.adapter = adapter
        }

        fun bind(item: RecentProducts) {
            binding.item = item
            adapter.submitList(item.recentProducts)
        }
    }

    class ShowMoreViewHolder(
        parent: ViewGroup,
        handler: InventoryEventHandler,
    ) : InventoryViewHolder<ItemInventoryShowMoreBinding>(
            ItemInventoryShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        ) {
        init {
            binding.handler = handler
        }

        fun bind(item: ShowMore) {
            binding.item = item
        }
    }

    class ProductViewHolder(
        parent: ViewGroup,
        handler: InventoryEventHandler,
    ) : InventoryViewHolder<ItemInventoryProductBinding>(
            ItemInventoryProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        ) {
        init {
            binding.handler = handler
        }

        fun bind(item: ProductUiModel) {
            binding.item = item
        }
    }
}
