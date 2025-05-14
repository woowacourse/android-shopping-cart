package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.PaginationButtonItemBinding

class PaginationButtonViewHolder(
    private val binding: PaginationButtonItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(
            parent: ViewGroup,
            cartViewModel: CartViewModel,
        ): PaginationButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PaginationButtonItemBinding.inflate(inflater, parent, false)
            binding.viewModel = cartViewModel
            return PaginationButtonViewHolder(binding)
        }
    }
}
