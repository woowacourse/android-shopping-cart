package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.getPriceText

class ProductDetailActivity : BindingActivity<ActivityProductDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }

    override fun initStartView() {
        setTitle("Detail")
        val id = intent.getLongExtra(EXTRA_PRODUCT_ID, -1L)
        if (id == -1L) finish()
        viewModel.loadById(id)

        viewModel.products.observe(this) { state ->
            when (state) {
                is UiState.Finish -> {
                    Glide.with(this)
                        .load(state.data.imgUrl)
                        .into(binding.ivProduct)
                    binding.tvName.text = state.data.name
                    val priceText = getPriceText(binding.root.context, state.data.price)
                    binding.tvPriceValue.text = priceText
                    binding.tvAddCart.setOnClickListener {
                        finish()
                        viewModel.saveCartItem(state.data)
                    }
                }

                is UiState.None -> {
                }

                is UiState.Error -> {
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "productId"

        fun start(
            context: Context,
            productId: Long,
        ) {
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                context.startActivity(this)
            }
        }
    }
}
