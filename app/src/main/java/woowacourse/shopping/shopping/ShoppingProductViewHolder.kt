package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.ProductUiModel

class ShoppingProductViewHolder private constructor(
    binding: ItemShoppingProductBinding,
//    val onProductImageClicked: (ProductUiModel) -> Unit,
//    val onAddToCartImageClicked: (ProductUiModel) -> Unit,
//    val onPlusButtonClicked: (ProductUiModel) -> Unit,
//    val onMinusButtonClicked: (ProductUiModel) -> Unit,
) :
    ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ShoppingProduct, ItemShoppingProductBinding>(
        binding
    ) {

    private lateinit var product: ShoppingRecyclerItem.ShoppingProduct

    fun setOnClicked(
        onProductImageClicked: (ProductUiModel) -> Unit,
        // TODO: - 숫자 + 이 부분을 커스텀 뷰로 만들어서 재사용
        // TODO: interface로 추상화
        onAddToCartImageClicked: (ProductUiModel) -> Unit,
        onPlusButtonClicked: (ProductUiModel) -> Unit,
        onMinusButtonClicked: (ProductUiModel) -> Unit,
    ) {
        with(binding) {
            imageProduct.setOnClickListener {
                onProductImageClicked(product.value)
            }
            imageAddToCart.setOnClickListener {
                it.isVisible = false
                layoutSelectProductCount.isVisible = true
                onAddToCartImageClicked(product.value)
            }
            buttonPlusProductCount.setOnClickListener {
                // TODO: 서버로부터 받아와야 하지 않을까..?
                // 아니면 add버튼을 누른 시점에 shoppingCartProduct로 생각해야할까???
                val productCount = textProductCount.text
                    .toString()
                    .toInt() + 1

                textProductCount.text = productCount.toString()
                onPlusButtonClicked(product.value)
            }
            buttonMinusProductCount.setOnClickListener {
                val productCount = textProductCount.text
                    .toString()
                    .toInt() - 1

                textProductCount.text = productCount.toString()
                onMinusButtonClicked(product.value)
            }
        }
    }

    override fun bind(itemData: ShoppingRecyclerItem.ShoppingProduct) {
        product = itemData

        with(binding) {
            Glide.with(binding.root.context)
                .load(product.value.imageUrl)
                .into(binding.imageProduct)

            textProductName.text = product.value.name
            textProductPrice.text = product.value.price.toString()
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShoppingProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingProductViewHolder(binding)
        }
    }
}
