package woowacourse.shopping.feature.goods.adapter.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemHorizontalSectionBinding
import woowacourse.shopping.feature.goods.GoodsViewModel

class HorizontalSelectionViewHolder(
    val binding: ItemHorizontalSectionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(
            parent: ViewGroup,
            lifecycleOwner: LifecycleOwner,
            viewModel: GoodsViewModel,
        ): HorizontalSelectionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemHorizontalSectionBinding.inflate(inflater, parent, false)
            binding.lifecycleOwner = lifecycleOwner
            binding.viewModel = viewModel
            return HorizontalSelectionViewHolder(binding)
        }
    }
}
