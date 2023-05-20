package woowacourse.shopping.ui.productdetail.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import woowacourse.shopping.databinding.OrderCountDialogBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.productdetail.contract.ProductDetailContract
import woowacourse.shopping.ui.productdetail.contract.presenter.ProductDetailPresenter

class ProductOrderDialog(
    context: Context,
    private val lifeCycleOwner: LifecycleOwner,
    val presenter: ProductDetailContract.Presenter,
    private val productDialogInterface: ProductDialogInterface,
    val product: ProductUIModel,
) : Dialog(context) {

    private lateinit var binding: OrderCountDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = OrderCountDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.product = product
        binding.lifecycleOwner = lifeCycleOwner
        binding.presenter = presenter as ProductDetailPresenter?
        binding.listener = productDialogInterface

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
