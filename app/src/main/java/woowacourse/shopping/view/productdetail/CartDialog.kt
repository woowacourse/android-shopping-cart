package woowacourse.shopping.view.productdetail

import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import woowacourse.shopping.data.CartProductSqliteProductRepository
import woowacourse.shopping.databinding.CartDialogBinding
import woowacourse.shopping.model.ProductModel

class CartDialog(private val context: ProductDetailActivity, private val cartRepository: CartProductSqliteProductRepository) {
    private val dialog = Dialog(context)
    private lateinit var binding: CartDialogBinding
    private lateinit var product: ProductModel
    private var productCount = 1

    fun show(productModel: ProductModel) {
        binding = CartDialogBinding.inflate(context.layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        setData(productModel)
        setBinding()
        setCountView()
        setClickListener()
        dialog.show()
    }

    private fun setData(productModel: ProductModel) {
        product = productModel
    }

    private fun setBinding() {
        binding.product = product
    }

    private fun setCountView() {
        binding.dialogCount.count = productCount
        binding.dialogCount.plusClickListener = { plusCount() }
        binding.dialogCount.minusClickListener = { subCount() }
    }

    private fun setClickListener() {
        binding.dialogPutButton.setOnClickListener {
            cartRepository.add(product.id, productCount, true)
            dialog.dismiss()
            context.startCartActivity()
        }
    }

    private fun plusCount() {
        productCount += 1
    }

    private fun subCount() {
        productCount -= 1
    }
}
