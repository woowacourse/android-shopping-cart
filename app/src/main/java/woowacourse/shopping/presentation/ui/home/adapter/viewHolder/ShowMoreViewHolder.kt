package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShowMoreBinding

class ShowMoreViewHolder(
    private val binding: ItemShowMoreBinding,
    private val clickProduct: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { clickProduct() }
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemShowMoreBinding =
            ItemShowMoreBinding.inflate(layoutInflater, parent, false)
    }
}
