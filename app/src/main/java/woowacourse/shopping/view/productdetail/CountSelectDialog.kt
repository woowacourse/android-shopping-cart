package woowacourse.shopping.view.productdetail

import android.app.Dialog
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.shopping.domain.CartProduct
import com.shopping.domain.Count
import com.shopping.repository.CartProductRepository
import woowacourse.shopping.databinding.DialogCountSelectBinding
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain
import woowacourse.shopping.view.customview.CounterView
import woowacourse.shopping.view.customview.CounterViewEventListener
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class CountSelectDialog(
    private val context: AppCompatActivity,
    private val cartProductRepository: CartProductRepository
) {

    private lateinit var binding: DialogCountSelectBinding
    private val dialog = Dialog(context)
    private var productCount = Count(1)

    fun show(product: ProductUIModel) {
        binding = DialogCountSelectBinding.inflate(context.layoutInflater)
        binding.product = product

        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)

        setDialogSize(dialog.window)

        binding.counterView.listener = object : CounterViewEventListener {
            override fun addCount(counterView: CounterView, count: Int) {
                binding.counterView.updateCountView()
                productCount = Count(count)
            }

            override fun decCount(counterView: CounterView, count: Int) {
                binding.counterView.updateCountView()
                productCount = Count(count)
            }
        }

        binding.btnAddToCart.setOnClickListener {
            saveCartProduct(product)
            showCartPage()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setDialogSize(window: Window?) {
        if (window == null) {
            return
        }
        val layoutParams = LayoutParams()
        layoutParams.copyFrom(window.attributes)
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
    }

    private fun saveCartProduct(product: ProductUIModel) {
        cartProductRepository.update(CartProduct(product.toDomain(), productCount))
    }

    private fun showCartPage() {
        context.startActivity(ShoppingCartActivity.intent(binding.root.context))
        context.finish()
    }
}
