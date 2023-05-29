package woowacourse.shopping.productdetail

import android.app.ActionBar.LayoutParams
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.databinding.DialogCartBinding
import woowacourse.shopping.model.ProductUIModel

class CartDialog(
    private val activity: Activity,
    private val product: ProductUIModel,
    private val onClickAdd: () -> Unit,
) : DialogFragment() {
    private lateinit var binding: DialogCartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogCartBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        binding.product = product
        binding.tvAddCart.setOnClickListener {
            onClickAdd()
        }
        return binding.root
    }
}
