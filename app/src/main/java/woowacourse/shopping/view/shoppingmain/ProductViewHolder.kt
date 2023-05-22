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
    private val productOnClick: (ProductUIModel) -> Unit,
    private val findCartCountById: (ProductUIModel) -> Int,
    private val addToCartOnClick: (ProductUIModel) -> Unit,
    private val saveCartProductCount: (ProductUIModel, Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_product_main, parent, false)
) {
    private val binding = ItemProductMainBinding.bind(itemView)
    private lateinit var product: ProductUIModel

    init {
        itemView.setOnClickListener {
            productOnClick(product)
        }

        binding.counterMainProduct.counterListener = object: CounterViewEventListener {
            override fun updateCount(counterView: CounterView, count: Int) {
                saveCartProductCount(product, count)
            }
        }

        binding.counterMainProduct.productCounterListener = object: ProductCounterViewEventListener {
            override fun onAddToCartButtonClick() {
                addToCartOnClick(product)
            }
        }
    }

    fun bind(item: ProductUIModel) {
        product = item
        binding.product = product
        binding.counterMainProduct.setViewVisibility(findCartCountById(product))
        binding.counterMainProduct.initCountView(findCartCountById(product))
    }
}
