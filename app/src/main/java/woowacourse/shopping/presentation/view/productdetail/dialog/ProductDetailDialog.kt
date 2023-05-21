package woowacourse.shopping.presentation.view.productdetail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.DialogFragmentProductDetailBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.getParcelableCompat

class ProductDetailDialog : DialogFragment() {
    private var _binding: DialogFragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private var eventListener: ProductDetailDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_fragment_product_detail,
            container,
            false
        )

        val product: ProductModel? = requireArguments().getParcelableCompat(KEY)
        if (product == null) {
            this.dismiss()
        }
        binding.product = product
        binding.btProductDetailAddToCart.setOnClickListener {
            eventListener?.onOrderClick(
                product!!.id,
                binding.productDetailFragmentCounterView.count
            )
            dismiss()
        }

        return binding.root
    }

    fun setEventListener(listener: ProductDetailDialogListener) {
        eventListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        eventListener = null
    }

    interface ProductDetailDialogListener {
        fun onOrderClick(productId: Long, count: Int)
    }

    companion object {
        const val TAG = "ProductDetailDialog"
        private const val KEY = "key"
        fun newInstance(value: ProductModel) = ProductDetailDialog().apply {
            arguments = bundleOf().apply {
                putParcelable(KEY, value)
            }
        }
    }
}
