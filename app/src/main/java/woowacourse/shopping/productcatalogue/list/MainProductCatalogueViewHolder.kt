package woowacourse.shopping.productcatalogue.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.databinding.ItemProductCatalogueBinding
import woowacourse.shopping.productcatalogue.ProductCountClickListener
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.view.CounterView

class MainProductCatalogueViewHolder(
    private val binding: ItemProductCatalogueBinding,
    productCountClickListener: ProductCountClickListener,
    productOnClick: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = productOnClick
        binding.countClickListener = productCountClickListener
    }

    fun bind(product: ProductUIModel, count: Int) {
        binding.cartProduct = CartProductUIModel(isPicked = true, count, product)
        binding.cvCounter.count = count
        binding.count = count
        binding.counterShowClickListener = object : OnCounterShowButtonClickListener {
            override fun onClick(counterView: CounterView, counterShow: FloatingActionButton) {
                counterView.visibility = View.VISIBLE
                counterShow.visibility = View.GONE
            }
        }
    }

    interface OnCounterShowButtonClickListener {
        fun onClick(counterView: CounterView, counterShow: FloatingActionButton)
    }

    companion object {
        fun getView(parent: ViewGroup): ItemProductCatalogueBinding {
            val inflater = LayoutInflater.from(parent.context)
            return ItemProductCatalogueBinding.inflate(inflater, parent, false)
        }
    }
}
