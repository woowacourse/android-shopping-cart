package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductHistoryBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class ProductHistoryListAdapter(private val actionHandler: ProductListActionHandler) :
    ListAdapter<Product, ProductHistoryListAdapter.ProductHistoryViewHolder>(ProductDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderProductHistoryBinding.inflate(inflater, parent, false)
        return ProductHistoryViewHolder(binding, actionHandler)
    }

    override fun onBindViewHolder(
        holder: ProductHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ProductHistoryViewHolder(
        private val binding: HolderProductHistoryBinding,
        private val actionHandler: ProductListActionHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productHistory: Product) {
            binding.productHistory = productHistory
            binding.actionHandler = actionHandler
            binding.executePendingBindings()
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
}
