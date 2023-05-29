package woowacourse.shopping.ui.productdetail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.common.utils.Toaster
import woowacourse.shopping.common.utils.getSerializableByKey
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.database.ShoppingDBOpenHelper
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.databinding.DialogAddCartProductBinding
import woowacourse.shopping.ui.model.ProductModel
import woowacourse.shopping.ui.model.ShoppingProductModel
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class CartProductDialog : DialogFragment(), CartProductDialogContract.View {
    private lateinit var binding: DialogAddCartProductBinding
    private lateinit var presenter: CartProductDialogContract.Presenter
    private lateinit var product: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializableByKey(BUNDLE_KEY_PRODUCT) ?: return dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding(inflater, container)
        initPresenter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCartProductAmountDecreaseButton()
        setupCartProductAmountIncreaseButton()
        setupAddToCartButton()
    }

    override fun updateCartProductAmount(amount: Int) {
        binding.dialogCartProductAmount.text = amount.toString()
    }

    override fun notifyAddToCartCompleted() {
        Toaster.showToast(requireContext(), TOAST_MESSAGE_ADD_TO_CART_COMPLETED)
        dismiss()
    }

    override fun notifyProductChanged(product: ShoppingProductModel, amountDifference: Int) {
        (activity as? ProductDetailActivity)?.addDifference(product)
        (activity as? ProductDetailActivity)?.addToAmountDifference(amountDifference)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DialogAddCartProductBinding.inflate(inflater, container, false)
        binding.product = product
    }

    private fun initPresenter() {
        val db = ShoppingDBOpenHelper(requireContext()).writableDatabase
        presenter = CartProductDialogPresenter(
            this,
            product,
            cartRepository = CartRepositoryImpl(CartDao(db)),
            cartProductAmount = 1
        )
    }

    private fun setupCartProductAmountDecreaseButton() {
        binding.dialogCartProductAmountMinusButton.setOnClickListener {
            presenter.decreaseCartProductAmount()
        }
    }

    private fun setupCartProductAmountIncreaseButton() {
        binding.dialogCartProductAmountPlusButton.setOnClickListener {
            presenter.increaseCartProductAmount()
        }
    }

    private fun setupAddToCartButton() {
        binding.dialogAddToCartButton.setOnClickListener {
            presenter.addToCart()
        }
    }

    companion object {
        private const val BUNDLE_KEY_PRODUCT = "product"
        private const val TOAST_MESSAGE_ADD_TO_CART_COMPLETED = "장바구니에 추가되었습니다!"

        fun createDialog(product: ProductModel): CartProductDialog {
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_KEY_PRODUCT, product)
            return CartProductDialog().apply { arguments = bundle }
        }
    }
}
