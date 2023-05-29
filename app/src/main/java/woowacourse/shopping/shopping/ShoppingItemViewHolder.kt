package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.ProductCountClickListener
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingItemViewHolder(
    private val binding: ItemShoppingProductBinding,
    private val countClickListener: ProductCountClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        productUiModel: ProductUiModel,
        onClicked: (ProductUiModel) -> Unit,
    ) {
        with(binding) {
            product = productUiModel
            root.setOnClickListener { onClicked(productUiModel) }

            mainCountView.updateBtnState(productUiModel.count)
            mainCountView.plusClickListener = {
                countClickListener.onPlusClick(productUiModel.id)
                productUiModel.count += CALCULATE_AMOUNT
            }
            mainCountView.minusClickListener = {
                countClickListener.onMinusClick(productUiModel.id)
                productUiModel.count -= CALCULATE_AMOUNT
            }
        }
    }

    companion object {
        const val PRODUCT_ITEM_TYPE = 0
        private const val CALCULATE_AMOUNT = 1

        fun from(
            parent: ViewGroup,
            countClickListener: ProductCountClickListener,
        ): ShoppingItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingItemViewHolder(binding, countClickListener)
        }
    }
}
