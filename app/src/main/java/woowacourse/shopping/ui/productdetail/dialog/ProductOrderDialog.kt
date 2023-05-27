package woowacourse.shopping.ui.productdetail.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import woowacourse.shopping.databinding.OrderCountDialogBinding
import woowacourse.shopping.model.ProductUIModel

class ProductOrderDialog(
    context: Context,
    private val productDialogInterface: ProductDialogInterface,
    val product: ProductUIModel,
) : Dialog(context) {

    private var count = 1

    private lateinit var binding: OrderCountDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = OrderCountDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.product = product
        binding.listener = productDialogInterface
        binding.count = count
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
