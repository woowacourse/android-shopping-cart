package woowacourse.shopping.view.product

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.showSnackBar
import woowacourse.shopping.view.common.showToast
import woowacourse.shopping.view.productDetail.ProductDetailActivity
import woowacourse.shopping.view.shoppingCart.ShoppingCartActivity

class ProductsActivity :
    AppCompatActivity(),
    ProductAdapter.ProductListener {
    private val binding: ActivityProductsBinding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }
    private val viewModel: ProductsViewModel by viewModels()
    private val productAdapter: ProductAdapter by lazy { ProductAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.productsRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initDataBinding()
        handleEventsFromViewModel()
        bindData()
        setupAdapter()
    }

    private fun initDataBinding() {
        binding.adapter = productAdapter
        binding.onClickShoppingCartButton = ::navigateToShoppingCart
        binding.lifecycleOwner = this
    }

    private fun handleEventsFromViewModel() {
        viewModel.event.observe(this) { event: ProductsEvent ->
            when (event) {
                ProductsEvent.UPDATE_PRODUCT_FAILURE -> showToast(getString(R.string.products_update_products_error_message))
                ProductsEvent.NOT_ADD_TO_SHOPPING_CART ->
                    binding.root.showSnackBar(
                        getString(R.string.product_detail_add_shopping_cart_error_message),
                    )
            }
        }
    }

    private fun bindData() {
        viewModel.products.observe(this) { products: List<ProductsItem> ->
            productAdapter.appendItems(products)
        }

        viewModel.shoppingCartQuantity.observe(this) { shoppingCartQuantity: Int ->
            binding.productsShoppingCartQuantity.apply {
                visibility =
                    if (shoppingCartQuantity > 0) View.VISIBLE else View.GONE
                text = shoppingCartQuantity.toString()
            }
        }
    }

    private fun setupAdapter() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (ProductsItem.ItemType.from(productAdapter.getItemViewType(position))) {
                        ProductsItem.ItemType.PRODUCT -> 1
                        ProductsItem.ItemType.MORE -> 2
                    }
            }

        binding.products.layoutManager = gridLayoutManager
    }

    private fun navigateToShoppingCart() {
        startActivity(ShoppingCartActivity.newIntent(this))
    }

    override fun onProductClick(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onPlusShoppingCartClick(product: Product) {
        viewModel.addProductToShoppingCart(product)
    }

    override fun onMinusShoppingCartClick(product: Product) {
        // TODO : 장바구니에 수량 1개 삭제 버튼 로직 추가
    }

    override fun onLoadClick() {
        viewModel.updateProducts()
    }
}
