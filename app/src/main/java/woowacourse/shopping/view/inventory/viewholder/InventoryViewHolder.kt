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
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem
import woowacourse.shopping.view.inventory.item.InventoryItem.RecentProductsItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore

sealed class InventoryViewHolder<BINDING : ViewBinding>(protected val binding: BINDING) :
    RecyclerView.ViewHolder(binding.root) {
    class RecentItemsListViewHolder(parent: ViewGroup) :
        InventoryViewHolder<ItemInventoryRecentListBinding>(
            ItemInventoryRecentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        ) {
        fun bind(
            item: RecentProductsItem,
            handler: InventoryEventHandler,
        ) {
            RecentListAdapter(handler).let { adapter ->
                binding.item = item
                binding.rvRecentList.adapter = adapter
                adapter.submitList(item.recentProducts)
            }
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
        private val handler: InventoryEventHandler,
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

        fun bind(item: ProductItem) {
            binding.item = item
            binding.tvDecreaseQuantity.setOnClickListener {
                handler.onDecreaseQuantity(adapterPosition, item)
            }
            binding.tvIncreaseQuantity.setOnClickListener {
                handler.onIncreaseQuantity(adapterPosition, item)
            }
            binding.ivAddProductIcon.setOnClickListener {
                handler.onIncreaseQuantity(adapterPosition, item)
            }
        }
    }
}
