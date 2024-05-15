package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.base.ViewModelFactory

class ProductDetailActivity : BindingActivity<ActivityProductDetailBinding>() {
    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }

    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    override fun initStartView() {
        supportActionBar?.title = getString(R.string.product_detail_title)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProductDetailActivity
        }

        initObserve()
    }

    private fun initObserve() {
        viewModel.message.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).apply {
                anchorView = binding.tvAddToCart
                show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_detai_closed -> finish()
        }
        return true
    }

    companion object {
        const val PUT_EXTRA_PRODUCT_ID = "product_id"

        fun startActivity(
            context: Context,
            id: Int,
        ) {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PUT_EXTRA_PRODUCT_ID, id)
            context.startActivity(intent)
        }
    }
}
