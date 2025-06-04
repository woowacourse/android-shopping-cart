package woowacourse.shopping.view.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ResultFrom
import woowacourse.shopping.view.common.getSerializableExtraData
import woowacourse.shopping.view.common.showSnackBar

class ProductDetailActivity :
    AppCompatActivity(),
    ProductDetailListener {
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.provideFactory(
            intent.getSerializableExtraData(EXTRA_PRODUCT)
                ?: error("상품 상세 화면: Product 가 전달되지 않았습니다."),
        )
    }

    private val binding: ActivityProductDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
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
                val intent =
                    Intent().apply {
                        putExtra("updateProduct", event.product)
                    }
                setResult(ResultFrom.PRODUCT_DETAIL_BACK.RESULT_OK, intent)
                finish()
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
                    Intent().apply {
                        putExtra("recentProduct", event.recentProduct)
                        putExtra("updateProduct", event.currentProduct)
                    }
                setResult(ResultFrom.PRODUCT_RECENT_WATCHING_CLICK.RESULT_OK, intent)
                finish()
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

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(
                    EXTRA_PRODUCT,
                    product,
                )
            }
    }
}
