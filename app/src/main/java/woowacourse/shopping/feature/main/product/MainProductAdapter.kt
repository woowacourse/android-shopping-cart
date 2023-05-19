package woowacourse.shopping.feature.main.product

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.ProductUiModel

class MainProductAdapter(
    private val listener: ProductClickListener
) : ListAdapter<ProductUiModel, MainProductViewHolder>(ProductDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        return MainProductViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun setItems(newItems: List<ProductUiModel>) {
        submitList(newItems)
    }

    companion object {
        const val VIEW_TYPE = 222

        private val ProductDiffUtil = object : DiffUtil.ItemCallback<ProductUiModel>() {
            override fun areItemsTheSame(
                oldItem: ProductUiModel,
                newItem: ProductUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductUiModel,
                newItem: ProductUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
