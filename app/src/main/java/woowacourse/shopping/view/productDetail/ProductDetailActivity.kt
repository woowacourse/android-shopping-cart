package woowacourse.shopping.view.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.getSerializableExtraData
import woowacourse.shopping.view.common.showSnackBar

class ProductDetailActivity :
    AppCompatActivity(),
    ProductDetailListener {
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.provideFactory(
            product =
                intent.getSerializableExtraData(EXTRA_PRODUCT)
                    ?: error("상품 상세 화면: Product 가 전달되지 않았습니다."),
            isRecentWatchingProduct =
                intent.getBooleanExtra(
                    EXTRA_IS_RECENT_WATCHING_PRODUCT,
                    false,
                ),
            previousUpdatedProduct =
                intent.getSerializableExtraData(EXTRA_UPDATED_PREVIOUS_PRODUCT),
        )
    }

    private val binding: ActivityProductDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            when (result.resultCode) {
                RECENT_PRODUCT_BACK_RESULT_OK -> {
                    val updateQuantityProduct: Product =
                        result.data?.getSerializableExtraData("updateProduct")
                            ?: return@registerForActivityResult
                    val recentProduct: Product =
                        result.data?.getSerializableExtraData("recentProduct")
                            ?: return@registerForActivityResult
                    setupRecentProductResult(
                        updateQuantityProduct,
                        recentProduct,
                        UPDATED_RECENT_PRODUCT_RESULT_OK,
                    )
                }
            }
        }

    private fun setupRecentProductResult(
        updateQuantityProduct: Product,
        recentProduct: Product,
        resultCode: Int,
    ) {
        val intent =
            Intent().apply {
                putExtra("updateProduct", updateQuantityProduct)
                putExtra("recentProduct", recentProduct)
            }
        setResult(resultCode, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.productDetailRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            insets
        }
        bindViewModel()
        setupObservers()
    }

    private fun bindViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.productDetailEventListener = this
    }

    private fun setupObservers() {
        viewModel.event.observe(this) { event: ProductDetailEvent ->
            handleEvent(event)
        }
    }

    private fun handleEvent(event: ProductDetailEvent) {
        when (event) {
            ProductDetailEvent.CartAdditionFailed ->
                binding.root.showSnackBar(
                    getString(R.string.product_detail_add_shopping_cart_error_message),
                )

            ProductDetailEvent.CartAdditionSucceeded ->
                binding.root.showSnackBar(
                    getString(
                        R.string.product_detail_add_shopping_cart_success_message,
                    ),
                )

            is ProductDetailEvent.UpdatedProductRequested -> {
                if (event.previousUpdatedProduct == null) {
                    val intent =
                        Intent().apply {
                            putExtra("updateProduct", event.product)
                        }
                    setResult(UPDATED_PRODUCT_RESULT_OK, intent)
                    finish()
                    return
                }
                setupRecentProductResult(
                    event.previousUpdatedProduct,
                    event.product,
                    RECENT_PRODUCT_BACK_RESULT_OK,
                )
            }

            ProductDetailEvent.RecentProductFetchFailed ->
                binding.root.showSnackBar(
                    getString(R.string.product_detail_update_recent_watching_error_message),
                )

            ProductDetailEvent.RecentProductAdditionFailed ->
                binding.root.showSnackBar(
                    getString(R.string.product_detail_add_recent_watching_error_message),
                )

            is ProductDetailEvent.RecentProductRequested -> {
                val intent =
                    newIntent(
                        context = this,
                        product = event.recentProduct,
                        isRecentWatchingProduct = true,
                        previousUpdatedProduct = event.currentProduct,
                    )
                activityResultLauncher.launch(intent)
            }
        }
    }

    override fun onCloseButtonClick() {
        viewModel.updateProductRequestedEvent()
    }

    override fun onAddingToShoppingCartClick() {
        viewModel.addToShoppingCart()
    }

    override fun onPlusShoppingCartClick(product: Product) {
        viewModel.plusQuantity()
    }

    override fun onMinusShoppingCartClick(product: Product) {
        viewModel.minusQuantity()
    }

    override fun onRecentProductClick(product: Product) {
        viewModel.updateRecentProductEvent()
    }

    companion object {
        private const val EXTRA_PRODUCT = "woowacourse.shopping.EXTRA_PRODUCT"
        private const val EXTRA_IS_RECENT_WATCHING_PRODUCT =
            "woowacourse.shopping.EXTRA_IS_RECENT_WATCHING_PRODUCT"
        private const val EXTRA_UPDATED_PREVIOUS_PRODUCT =
            "woowacourse.shopping.EXTRA_UPDATED_PREVIOUS_PRODUCT"
        const val UPDATED_PRODUCT_RESULT_OK = 200
        const val UPDATED_RECENT_PRODUCT_RESULT_OK = 201
        const val RECENT_PRODUCT_BACK_RESULT_OK = 202

        fun newIntent(
            context: Context,
            product: Product,
            isRecentWatchingProduct: Boolean = false,
            previousUpdatedProduct: Product? = null,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(
                    EXTRA_PRODUCT,
                    product,
                )
                putExtra(
                    EXTRA_IS_RECENT_WATCHING_PRODUCT,
                    isRecentWatchingProduct,
                )
                putExtra(
                    EXTRA_UPDATED_PREVIOUS_PRODUCT,
                    previousUpdatedProduct,
                )
            }
    }
}
