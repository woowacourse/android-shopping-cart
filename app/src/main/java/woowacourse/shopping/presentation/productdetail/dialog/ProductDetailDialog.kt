package woowacourse.shopping.presentation.productdetail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.DialogProductDetailBinding
import woowacourse.shopping.presentation.common.CounterListener
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.productdetail.ProductDetailContract

class ProductDetailDialog(
    private val cartProductModel: CartProductInfoModel,
    private val presenter: ProductDetailContract.Presenter,
) : DialogFragment() {

    lateinit var binding: DialogProductDetailBinding

    private val counterListener = object : CounterListener {
        override fun onPlus(count: Int) {
            presenter.updateTotalPrice(count)
        }

        override fun onMinus(count: Int) {
            presenter.updateTotalPrice(count)
        }
    }

    fun setTotalPrice(totalPrice: Int) {
        binding.textDialogCartProductPrice.text = getString(R.string.price_format, totalPrice)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        addCartButtonClick()
    }

    private fun setUpBinding() {
        binding.lifecycleOwner = this
        binding.cartProduct = cartProductModel
        binding.counterDialogCartProduct.setUpView(counterListener = counterListener, 1, 1)
    }

    private fun addCartButtonClick() {
        binding.buttonDialogPutCart.setOnClickListener {
            presenter.saveProductInRepository(binding.counterDialogCartProduct.count)
            dismiss()
        }
    }
}
