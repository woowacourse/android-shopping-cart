package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModelFactory(DummyProductList),
            )[ProductDetailViewModel::class.java]

        binding =
            ActivityProductDetailBinding.inflate(layoutInflater).apply {
                vm = viewModel
                lifecycleOwner = this@ProductDetailActivity
            }
        setContentView(binding.root)
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
