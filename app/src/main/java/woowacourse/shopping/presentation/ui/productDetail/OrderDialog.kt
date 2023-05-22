package woowacourse.shopping.presentation.ui.productDetail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.shoppingCart.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.ShoppingCartRepositoryImpl
import woowacourse.shopping.databinding.DialogOrderBinding
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartActivity

class OrderDialog : DialogFragment() {
    private lateinit var binding: DialogOrderBinding
    private val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(
            ProductDao(requireContext()),
            ShoppingCartDao(requireContext()),
        )
    }
    private val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl(
            ShoppingCartDao(requireContext()),
            ProductDao(requireContext()),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setCounter()
        clickPut()
    }

    override fun onResume() {
        super.onResume()
        setWidth()
    }

    private fun setWidth() {
        val windowManager: WindowManager =
            requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            (windowManager.currentWindowMetrics.bounds.width() * 0.95).toInt()
        } else {
            (windowManager.defaultDisplay.width * 0.95).toInt()
        }
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun clickPut() {
        binding.buttonOrderDialogPut.setOnClickListener {
            shoppingCartRepository.increaseProductQuantity(
                binding.product?.id ?: return@setOnClickListener,
                binding.customOrderDialogCounter.currentQuantity,
            )
            dismiss()
            startActivity(ShoppingCartActivity.getIntent(requireContext()))
            requireActivity().finish()
        }
    }

    private fun setCounter() {
        binding.customOrderDialogCounter.setMinValue(1)
        binding.customOrderDialogCounter.setQuantityText(1)
        binding.customOrderDialogCounter.setIncreaseClickListener {
            val increasedQuantity = binding.customOrderDialogCounter.currentQuantity + 1
            binding.customOrderDialogCounter.setQuantityText(increasedQuantity)
            calculateTotalPrice()
        }
        binding.customOrderDialogCounter.setDecreaseClickListener {
            val decreasedQuantity = binding.customOrderDialogCounter.currentQuantity - 1
            binding.customOrderDialogCounter.setQuantityText(decreasedQuantity)
            calculateTotalPrice()
        }
    }

    private fun getData() {
        val productId: Long = arguments?.getLong(PRODUCT_ID) ?: return dismiss()
        val result = productRepository.getProduct(productId)
        when (result) {
            is WoowaResult.SUCCESS -> binding.product = result.data
            is WoowaResult.FAIL -> return dismiss()
        }
    }

    private fun calculateTotalPrice() {
        val price: Int = binding.product?.price ?: 0
        val quantity: Int = binding.customOrderDialogCounter.currentQuantity
        binding.textOrderDialogPrice.text = (price * quantity).toString()
    }

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"
        fun makeDialog(productId: Long): OrderDialog {
            return OrderDialog().apply {
                val bundle = Bundle()
                bundle.putLong("PRODUCT_ID", productId)
                arguments = bundle
            }
        }
    }
}
