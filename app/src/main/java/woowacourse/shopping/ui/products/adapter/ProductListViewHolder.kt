package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class ProductListViewHolder private constructor(
    private val binding: ItemProductBinding,
    private val onClick: (Long) -> Unit,
    private val onClickAddToCartButton: (Long) -> Unit,
    private val onClickPlusCount: (Long) -> Unit,
    private val onClickMinusCount: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onClick(binding.product?.id ?: return@setOnClickListener) }
        binding.btnAddToCart.setOnClickListener {
            onClickAddToCartButton(binding.product?.id ?: return@setOnClickListener)
        }
        binding.counter.tvPlus.setOnClickListener {
            onClickPlusCount(binding.product?.cartItemId ?: return@setOnClickListener)
        }
        binding.counter.tvMinus.setOnClickListener {
            onClickMinusCount(binding.product?.cartItemId ?: return@setOnClickListener)
        }
    }

    fun bind(product: ProductUIState) {
        binding.product = product
        binding.tvProductPrice.text = itemView.context.getString(R.string.product_price)
            .format(PRICE_FORMAT.format(product.price))
        Glide.with(itemView)
            .load(product.imageUrl)
            .into(binding.ivProduct)
        bindCartUI(product)
    }

    private fun bindCartUI(product: ProductUIState) {
        binding.btnAddToCart.isVisible = product.count == null
        binding.counter.isVisible = product.cartItemId != null
        if (product.count == null || product.cartItemId == null) return
        binding.counter.isVisible = true
        binding.counter.count = product.count
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (Long) -> Unit,
            onClickAddToCartButton: (Long) -> Unit,
            onClickPlusCount: (Long) -> Unit,
            onClickMinusCount: (Long) -> Unit
        ): ProductListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            val binding = ItemProductBinding.bind(view)
            return ProductListViewHolder(
                binding,
                onClick,
                onClickAddToCartButton,
                onClickPlusCount,
                onClickMinusCount
            )
        }
    }
}