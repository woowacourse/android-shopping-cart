package woowacourse.shopping.shopping.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.shopping.ProductItem
import woowacourse.shopping.shopping.ProductsItemType

class ProductsViewHolder private constructor(
    private val binding: ProductItemBinding,
    val onClickItem: (Int) -> Unit,
    val onClickAdd: (Int) -> Unit,
    private val onClickPlus: (Int) -> Unit,
    private val onClickMinus: (Int) -> Unit,
) :
    ItemViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClickItem(bindingAdapterPosition)
        }
        binding.imgAddCart.setOnClickListener {
            it.visibility = View.GONE
            binding.customCountView.visibility = View.VISIBLE
            onClickAdd(bindingAdapterPosition)
        }
        binding.customCountView.plusClickListener = {
            onClickPlus(bindingAdapterPosition)
        }
        binding.customCountView.minusClickListener = {
            onClickMinus(bindingAdapterPosition)
        }
    }

    fun bind(productItemType: ProductsItemType) {
        val productItem = productItemType as? ProductItem ?: return
        binding.product = productItem.product
        binding.customCountView.count = 1
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickItem: (Int) -> Unit,
            onClickAdd: (Int) -> Unit,
            onClickPlus: (Int) -> Unit,
            onClickMinus: (Int) -> Unit,
        ): ProductsViewHolder {
            val binding = ProductItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductsViewHolder(binding, onClickItem, onClickAdd, onClickPlus, onClickMinus)
        }
    }
}
