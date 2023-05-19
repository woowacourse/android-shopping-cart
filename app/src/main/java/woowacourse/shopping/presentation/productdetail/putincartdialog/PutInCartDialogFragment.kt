package woowacourse.shopping.presentation.productdetail.putincartdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentPutInCartDialogBinding

class PutInCartDialogFragment(
    private val putInCartByCount: (count: Int) -> Unit,
) : DialogFragment() {

    private var _binding: FragmentPutInCartDialogBinding? = null
    private val binding get() = _binding!!

    private var count: Int = INITIAL_COUNT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_put_in_cart_dialog, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductNameAndPrice()
        initView()
    }

    private fun initProductNameAndPrice() {
        val productName = arguments?.getString(PRODUCT_NAME_KEY) ?: return dismiss()
        val productPrice = arguments?.getInt(PRODUCT_PRICE_KEY) ?: return dismiss()

        binding.textCartProductName.text = productName
        binding.textCartProductPrice.text = getString(R.string.price_format, productPrice)
    }

    private fun initView() {
        initPutInCartListener()
        setCountView()
        setAddButtonListener()
        setRemoveButtonListener()
    }

    private fun initPutInCartListener() {
        binding.buttonPutInCart.setOnClickListener {
            putInCartByCount(count)
        }
    }

    private fun setCountView() {
        binding.textCartCount.text = count.toString()
    }

    private fun setAddButtonListener() {
        binding.imageCartCountAdd.setOnClickListener {
            addCountEvent()
        }
    }

    private fun setRemoveButtonListener() {
        binding.imageCartCountRemove.setOnClickListener {
            removeCountEvent()
        }
    }

    private fun addCountEvent() {
        count += ADD_REMOVE_UNIT
        setCountView()
    }

    private fun removeCountEvent() {
        if (count <= INITIAL_COUNT) return
        count -= ADD_REMOVE_UNIT
        setCountView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "PunInCartDialogFragment"
        const val PRODUCT_NAME_KEY = "PRODUCT_NAME_KEY"
        const val PRODUCT_PRICE_KEY = "PRODUCT_PRICE_KEY"
        const val INITIAL_COUNT = 1
        const val ADD_REMOVE_UNIT = 1

        fun newInstance(
            name: String,
            price: Int,
            putInCartByCount: (count: Int) -> Unit,
        ): PutInCartDialogFragment {
            val fragment = PutInCartDialogFragment(putInCartByCount)
            val args = Bundle().apply {
                putString(PRODUCT_NAME_KEY, name)
                putInt(PRODUCT_PRICE_KEY, price)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
