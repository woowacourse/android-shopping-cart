package woowacourse.shopping.feature.product.detail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.data.datasource.cartdatasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.db.cart.CartDbHelper
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.FragmentCounterDialogBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.extension.getParcelableCompat
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

class CounterDialog : DialogFragment(), CounterDialogContract.View {
    override lateinit var presenter: CounterDialogContract.Presenter
    private var _binding: FragmentCounterDialogBinding? = null
    private val binding: FragmentCounterDialogBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCounterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product =
            arguments?.getParcelableCompat<CartProductItem>(PRODUCT_KEY) ?: return dismiss()
        val cartDbHelper = CartDbHelper(requireContext())
        val cartLocalDataSource = CartLocalDataSourceImpl(cartDbHelper)
        val cartRepository = CartRepositoryImpl(cartLocalDataSource)
        presenter = CounterDialogPresenter(this, cartRepository, product)
        binding.product = product
        setListener()

        presenter.loadInitialData()
    }

    override fun setCountState(count: Int) {
        binding.productCountTv.text = "$count"
    }

    private fun setListener() {
        binding.addToCartButton.setOnClickListener {
            presenter.addCart()
            CartActivity.startActivity(requireContext())
        }
        binding.productPlusTv.setOnClickListener { presenter.updateCount(PLUS) }
        binding.productMinusTv.setOnClickListener {
            if ((binding.productCountTv.text == MIN_COUNT).not()) {
                presenter.updateCount(MINUS)
            }
        }
    }

    override fun exit() {
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PRODUCT_KEY = "product"
        const val COUNTER_DIALOG_TAG = "counter_dialog"
        private const val MIN_COUNT = "1"
        private const val PLUS = 1
        private const val MINUS = -1

        @JvmStatic
        fun newInstance(product: CartProductItem) =
            CounterDialog().apply {
                arguments = bundleOf(
                    PRODUCT_KEY to product.copy(),
                )
            }
    }
}
