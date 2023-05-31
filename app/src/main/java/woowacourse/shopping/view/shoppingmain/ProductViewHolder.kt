package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductMainBinding
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.view.customview.CounterView
import woowacourse.shopping.view.customview.CounterViewEventListener
import woowacourse.shopping.view.customview.ProductCounterViewEventListener

class ProductViewHolder(
    parent: ViewGroup,
    private val shoppingMainClickListener: ShoppingMainClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_product_main, parent, false)
) {
    private val binding = ItemProductMainBinding.bind(itemView)
    private lateinit var product: ProductUIModel

    init {
        itemView.setOnClickListener {
            shoppingMainClickListener.productOnClick(product)
        }

        binding.counterMainProduct.counterListener = object : CounterViewEventListener {
            override fun updateCount(counterView: CounterView, count: Int): Int {
                shoppingMainClickListener.saveCartProductCount(product, count)
                return count
            }
        }

        binding.counterMainProduct.productCounterListener = object : ProductCounterViewEventListener {
            override fun onAddToCartButtonClick() {
                shoppingMainClickListener.addToCartOnClick(product)
            }
        }
    }

    fun bind(item: ProductUIModel) {
        product = item
        binding.product = product
        binding.counterMainProduct.setViewVisibility(shoppingMainClickListener.findCartCountById(product))
        binding.counterMainProduct.initCountView(shoppingMainClickListener.findCartCountById(product))
    }
}
