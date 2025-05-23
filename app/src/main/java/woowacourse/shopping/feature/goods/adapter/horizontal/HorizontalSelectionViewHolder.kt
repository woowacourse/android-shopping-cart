package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHorizontalSectionBinding

class HorizontalSelectionViewHolder(
    val binding: ItemHorizontalSectionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): HorizontalSelectionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemHorizontalSectionBinding.inflate(inflater, parent, false)
            return HorizontalSelectionViewHolder(binding)
        }
    }
}
