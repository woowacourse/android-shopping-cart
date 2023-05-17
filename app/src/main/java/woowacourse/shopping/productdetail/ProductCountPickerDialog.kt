package woowacourse.shopping.productdetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.databinding.DialogAddToShoppingCartBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.getSerializableCompat

class ProductCountPickerDialog : DialogFragment() {

    private var product: ProductUiModel? = null
    private var listener: ProductCountPickerListener? = null
    private var _binding: DialogAddToShoppingCartBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        arguments?.let {
            product = it.getSerializableCompat(KEY_PRODUCT)
            listener = it.getSerializableCompat(KEY_LISTENER)
        }
        _binding = DialogAddToShoppingCartBinding.inflate(layoutInflater, container, false)

        dialog?.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            product?.apply {
                textProductName.text = name
                textProductPrice.text = price.toString()
            }
            listener?.apply {
                buttonMinusProductCount.setOnClickListener {
                    onMinus(textProductCount.text.toString().toInt())
                }
                buttonPlusProductCount.setOnClickListener {
                    onPlus(textProductCount.text.toString().toInt())
                }
                buttonAddToCart.setOnClickListener {
                    onCompleted()
                    dismiss()
                }
            }
        }
    }

    fun setTextProductCount(count: Int) {
        binding.textProductCount.text = count.toString()
    }

    fun setTextTotalPrice(price: Int) {
        binding.textProductPrice.text = price.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "COUNT_PICKER_DIALOG"
        private const val KEY_LISTENER = "COUNT_PICKER_DIALOG"
        private const val KEY_PRODUCT = "PRODUCT"

        fun newInstance(
            product: ProductUiModel,
            listener: ProductCountPickerListener,
        ): ProductCountPickerDialog {

            return ProductCountPickerDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LISTENER, listener)
                    putSerializable(KEY_PRODUCT, product)
                }
            }
        }
    }
}
