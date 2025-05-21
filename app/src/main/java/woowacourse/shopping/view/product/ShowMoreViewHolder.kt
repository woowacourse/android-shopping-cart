package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ShowMoreBinding

class ShowMoreViewHolder(
    val binding: ShowMoreBinding,
    val toShowMore: () -> Boolean,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.toShowMore = ::toShowMore
    }

    companion object {
        fun from(
            parent: ViewGroup,
            toShowMore: () -> Boolean,
        ): ShowMoreViewHolder {
            val binding =
                ShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ShowMoreViewHolder(binding, toShowMore)
        }
    }
}
