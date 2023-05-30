package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

class ProductViewHolder(
    val parent: ViewGroup,
    onItemClick: (Int) -> Unit,
    onAddClick: (Int) -> Unit,
    onPlusClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_product, parent, false),
) {
    private val binding = ItemProductBinding.bind(itemView)

    init {
        binding.root.setOnClickListener { onItemClick(adapterPosition) }
        binding.productAdd.setOnClickListener {
            onAddClick(adapterPosition)
            setAddButtonVisibility()
        }

        binding.productPlusTv.setOnClickListener {
            onPlusClick(adapterPosition)
        }

        binding.productMinusTv.setOnClickListener {
            if (binding.productCountTv.text == MIN_COUNT) {
                setAddButtonVisibility()
                return@setOnClickListener
            }
            onMinusClick(adapterPosition)
        }
    }

    override fun bind(productView: ProductView) {
        val productItem = productView as CartProductItem
        binding.product = productItem
    }

    private fun setAddButtonVisibility() {
        val isVisible = binding.productAdd.isVisible

        binding.productAdd.isVisible = !isVisible
        binding.productAmountLayout.isVisible = isVisible
    }

    companion object {
        private const val MIN_COUNT = "1"
    }
}
