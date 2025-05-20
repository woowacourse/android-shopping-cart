package woowacourse.shopping.feature.goods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreButtonBinding

class MoreButtonViewHolder(
    binding: ItemMoreButtonBinding,
    private val onClick: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnMore.setOnClickListener { onClick() }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: () -> Unit,
        ): MoreButtonViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMoreButtonBinding.inflate(inflater, parent, false)
            return MoreButtonViewHolder(binding, onClick)
        }
    }
}
