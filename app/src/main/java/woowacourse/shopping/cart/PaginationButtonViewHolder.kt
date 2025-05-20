package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.CartItem.PaginationButtonItem
import woowacourse.shopping.databinding.PaginationButtonItemBinding

class PaginationButtonViewHolder(
    private val binding: PaginationButtonItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(paginationButtonItem: PaginationButtonItem) {
        binding.paginationButtonItem = paginationButtonItem
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onPaginationButtonClick: PaginationButtonClickListener,
        ): PaginationButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PaginationButtonItemBinding.inflate(inflater, parent, false)
            binding.clickListener = onPaginationButtonClick
            return PaginationButtonViewHolder(binding)
        }
    }
}
