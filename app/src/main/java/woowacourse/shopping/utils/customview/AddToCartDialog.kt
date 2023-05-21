package woowacourse.shopping.utils.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LayoutAddToCartDialogBinding
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class AddToCartDialog(
    private val product: ProductDetailUIState,
    private val onClickButton: (Long, Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: LayoutAddToCartDialogBinding

    private var productName: String
        get() = binding.tvProductName.toString()
        set(value) {
            binding.tvProductName.text = value
        }

    private var productPrice: Int
        get() = binding.tvProductPrice.toString().toInt()
        set(value) {
            binding.tvProductPrice.text = binding.root.resources.getString(R.string.product_price)
                .format(PRICE_FORMAT.format(value))
        }

    private var count: Int
        get() = binding.counter.count
        set(value) {
            binding.counter.count = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutAddToCartDialogBinding.inflate(LayoutInflater.from(context), container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.productName = product.name
        this.productPrice = product.price
        binding.btnAddToCart.setOnClickListener {
            onClickButton(product.id, count)
        }
        count = 1
        binding.counter.tvMinus.setOnClickListener { if (count > 1) count-- }
        binding.counter.tvPlus.setOnClickListener { count += 1 }
    }
}