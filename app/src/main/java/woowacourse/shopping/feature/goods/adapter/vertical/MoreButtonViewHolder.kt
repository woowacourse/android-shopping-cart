package woowacourse.shopping.feature.goods.adapter.vertical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreButtonBinding

class MoreButtonViewHolder(
    binding: ItemMoreButtonBinding,
    moreButtonClickListener: MoreButtonClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clickListener = moreButtonClickListener
    }

    companion object {
        fun from(
            parent: ViewGroup,
            moreButtonClickListener: MoreButtonClickListener,
        ): MoreButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMoreButtonBinding.inflate(inflater, parent, false)
            return MoreButtonViewHolder(binding, moreButtonClickListener)
        }
    }
}
