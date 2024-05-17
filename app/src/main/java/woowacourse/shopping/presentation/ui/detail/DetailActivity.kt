package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import woowacourse.shopping.R
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.ui.cart.CartActivity

class DetailActivity : AppCompatActivity(), DetailClickListener {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by lazy {
        val factory =
            DetailViewModelFactory(
                CartRepositoryImpl(),
                ShoppingItemsRepositoryImpl(),
                productId = productId,
            )
        factory.create(DetailViewModel::class.java)
    }
    private val productId: Long by lazy { intent.getLongExtra(PRODUCT_ID, INVALID_PRODUCT_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()

        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.vmProduct = viewModel
    }


    private fun setUpToolbar() {
        val toolbar: MaterialToolbar = binding.toolbarDetail
        setSupportActionBar(toolbar)

        toolbar.setOnMenuItemClickListener {
            finish()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(productId: Long) {
        viewModel.createShoppingCartItem()
        navigate()
    }

    private fun navigate() {
        startActivity(
            CartActivity.createIntent(context = this),
        )
    }

    companion object {
        const val PRODUCT_ID = "product_id"
        const val INVALID_PRODUCT_ID = -1L

        fun createIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
        }
    }
}
