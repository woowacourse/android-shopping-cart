package woowacourse.shopping.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityDetailProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.Extra
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.getSerializableExtraCompat

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(
            applicationContext,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        binding.lifecycleOwner = this

        initInsets()
        setupToolbar()

        val product = intent.getSerializableExtraCompat<Product>(Extra.KEY_PRODUCT_DETAIL)

        initListeners(product)
        observeViewModel()
        viewModel.fetchData(product)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_product_detail_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbDetailProduct)
        supportActionBar?.title = null
    }

    private fun initListeners(product: Product) {
        binding.btnProductDetailAddCart.setOnClickListener {
            viewModel.addToCart(product)
        }
    }

    private fun observeViewModel() {
        viewModel.product.observe(this) {
            binding.product = it
        }

        viewModel.resultState.observe(this) { result ->
            when (result) {
                ResultState.INSERT_SUCCESS -> showToast(R.string.product_detail_add_cart_toast_insert_success)
                ResultState.INSERT_FAILURE -> showToast(R.string.product_detail_add_cart_toast_insert_failure)
                else -> Unit
            }
        }
    }

    private fun showToast(
        @StringRes messageResId: Int,
    ) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java)
                .apply { putExtra(Extra.KEY_PRODUCT_DETAIL, product) }
    }
}
