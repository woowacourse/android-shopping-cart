package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.PaginationButtonItemBinding

class PaginationButtonViewHolder(
    private val binding: PaginationButtonItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        page: Int,
        isNextButtonEnabled: Boolean,
        isPrevButtonEnabled: Boolean,
    ) {
        binding.page = page + 1
        binding.isNextButtonEnabled = isNextButtonEnabled
        binding.isPrevButtonEnabled = isPrevButtonEnabled
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            handler: CartEventHandler,
        ): PaginationButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PaginationButtonItemBinding.inflate(inflater, parent, false)
            binding.handler = handler
            return PaginationButtonViewHolder(binding)
        }
    }
}
