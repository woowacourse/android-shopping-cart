package woowacourse.shopping.ui.recentProduct.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductItemItemBinding
import woowacourse.shopping.ui.recentProduct.RecentProductItem

class RecentProductViewHolder private constructor(
    private val binding: RecentProductItemItemBinding,
    onClickItem: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(recentProduct: RecentProductItem) {
        binding.product = recentProduct.product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickItem: (Int) -> Unit,
        ): RecentProductViewHolder {
            val binding =
                RecentProductItemItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return RecentProductViewHolder(binding, onClickItem)
        }
    }
}
