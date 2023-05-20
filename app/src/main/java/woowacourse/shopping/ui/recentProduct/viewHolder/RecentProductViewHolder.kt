package woowacourse.shopping.ui.recentProduct.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.RecentProductItemItemBinding
import woowacourse.shopping.ui.recentProduct.RecentProductItem
import woowacourse.shopping.ui.shopping.viewHolder.ProductsOnClickListener

class RecentProductViewHolder private constructor(
    private val binding: RecentProductItemItemBinding,
    onClickListener: ProductsOnClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = onClickListener
    }

    fun bind(recentProduct: RecentProductItem) {
        binding.product = recentProduct.product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickListener: ProductsOnClickListener,
        ): RecentProductViewHolder {
            val binding =
                RecentProductItemItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return RecentProductViewHolder(binding, onClickListener)
        }
    }
}
