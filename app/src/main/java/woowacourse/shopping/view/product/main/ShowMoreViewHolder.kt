package woowacourse.shopping.view.product.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ComponentShowMoreBinding

class ShowMoreViewHolder(
    val binding: ComponentShowMoreBinding,
    val toShowMore: () -> Boolean,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.toShowMore = toShowMore
    }

    companion object {
        fun from(
            parent: ViewGroup,
            toShowMore: () -> Boolean,
        ): ShowMoreViewHolder {
            val binding =
                ComponentShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShowMoreViewHolder(binding, toShowMore)
        }
    }
}
