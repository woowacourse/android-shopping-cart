package woowacourse.shopping.presentation.productdetail.ui

import LastProductRepositoryImpl
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel
import woowacourse.shopping.util.DatabaseProvider

class ProductDetailActivity : BindingActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    val viewModel: ProductDetailViewModel by viewModels {
        provideProductDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        removeSupportActionBarTitle()
        updateProductDetail()
        updateLastProduct()
        initViewBinding()

        viewModel.putProductFlag.observe(this) {
            Toast.makeText(this, getString(R.string.product_detail_cart_add_success), Toast.LENGTH_SHORT).show()
        }
        viewModel.finishFlag.observe(this) {
            finish()
        }
        viewModel.latestProduct.observe(this){ lastProduct ->
            binding.lastProduct = lastProduct
        }
        viewModel.product.observe(this) { product ->
            if (product != Product.INVALID_PRODUCT) {
                viewModel.addLastProduct()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_product_detail_close) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun removeSupportActionBarTitle() {
        supportActionBar?.title = null
    }

    private fun updateProductDetail() {
        viewModel.updateProductDetail(intent.getIntExtra(KEY_PRODUCT_ID, 0))
    }

    private fun updateLastProduct(){
        viewModel.loadLatestViewedProduct()
    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    companion object {
        private const val KEY_PRODUCT_ID = "PRODUCT_ID"

        fun newIntent(
            context: Context,
            id: Int,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT_ID, id)
            }
    }
}

fun provideProductDetailViewModelFactory(
    context: Context
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val appContext = context.applicationContext

            val productDao = DatabaseProvider.getDatabase(appContext).productDao()
            val cartDao = DatabaseProvider.getDatabase(appContext).cartDao()
            val lastProductDao  = DatabaseProvider.getDatabase(appContext).lastProductDao()

            val productRepository = ProductRepositoryImpl(productDao)
            val cartRepository = CartRepositoryImpl(cartDao, productDao)
            val lastProductRepository = LastProductRepositoryImpl(lastProductDao,productDao)

            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(productRepository, cartRepository,lastProductRepository) as T
        }
    }
}
