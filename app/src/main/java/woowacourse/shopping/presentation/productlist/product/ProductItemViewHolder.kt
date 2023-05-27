package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.CartProductModel

class ProductItemViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_product, parent, false)) {

    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup, productClickListener: ProductClickListener) :
        this(parent, LayoutInflater.from(parent.context), productClickListener)

    private val binding = ItemProductBinding.bind(itemView)

    init {
        binding.productClickListener = productClickListener
    }

    fun bind(cartProductModel: CartProductModel) {
        binding.cartProductModel = cartProductModel
        binding.itemCountCart.itemCount
        checkVisible(cartProductModel.count)
    }

    private fun checkVisible(productCount: Int) {
        val showCount = productCount >= 1
        binding.itemCountCart.root.isVisible = showCount
        binding.imageCountPlusBack.isVisible = !showCount
    }
}
