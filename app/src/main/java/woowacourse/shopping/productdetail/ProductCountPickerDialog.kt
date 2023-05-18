package woowacourse.shopping.productdetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.databinding.DialogCountPickerBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.getSerializableCompat

class ProductCountPickerDialog : DialogFragment() {

    private var addingCartListener: AddingCartListener? = null

    private var _binding: DialogCountPickerBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogCountPickerBinding.inflate(layoutInflater, container, false)

        arguments?.let {
            it.getSerializableCompat<CountPickerListener>(KEY_COUNT_PICKER_LISTENER)?.let { listener ->
                binding.countPicker.setListener(listener)
            }
            addingCartListener = it.getSerializableCompat(KEY_ADDING_CART_LISTENER)
            binding.product = it.getSerializableCompat(KEY_PRODUCT)
        }
        dialog?.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            addingCartListener?.apply {
                buttonAddToCart.setOnClickListener {
                    onAdded()
                    dismiss()
                }
            }
        }
    }

    fun setTextProductCount(count: Int) {
        binding.countPicker.setTextCount(count)
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
        private const val KEY_COUNT_PICKER_LISTENER = "COUNT_PICKER"
        private const val KEY_ADDING_CART_LISTENER = "ADDING_CART"
        private const val KEY_PRODUCT = "PRODUCT"

        fun newInstance(
            product: ProductUiModel,
            addingCartListener: AddingCartListener,
            countPickerListener: CountPickerListener,
        ): ProductCountPickerDialog {

            return ProductCountPickerDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_COUNT_PICKER_LISTENER, countPickerListener)
                    putSerializable(KEY_ADDING_CART_LISTENER, addingCartListener)
                    putSerializable(KEY_PRODUCT, product)
                }
            }
        }
    }
}
