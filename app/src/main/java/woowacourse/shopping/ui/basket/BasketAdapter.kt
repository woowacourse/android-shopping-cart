package woowacourse.shopping.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.ui.model.UiProduct

class BasketAdapter(private val onItemClick: (UiProduct) -> Unit) :
    ListAdapter<UiProduct, BasketViewHolder>(productDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding =
            ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val productDiffUtil = object : DiffUtil.ItemCallback<UiProduct>() {
            override fun areItemsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem == newItem
        }
    }
}
