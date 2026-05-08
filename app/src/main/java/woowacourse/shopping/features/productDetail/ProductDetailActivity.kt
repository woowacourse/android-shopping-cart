package woowacourse.shopping.features.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.data.DataProvider

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val parcelProduct = intent.getParcelableExtra<ParcelProduct>("PRODUCT")!!

        setContent {
            val viewModel: ProductDetailViewModel = viewModel(
                factory = ProductDetailViewModelFactory(
                    product = parcelProduct,
                    cartRepository = DataProvider.cartRepository,
                )
            )
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    productName = parcelProduct.name,
                    productImageUrl = parcelProduct.imageUrl,
                    onAddToCartClick = {
                        viewModel.addToCart()
                        Toast.makeText(this, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    },
                    onIncreaseClick = {
                        viewModel.increaseCartItem()
                    },
                    onDecreaseClick = {
                        viewModel.decreaseCartItem()
                    },
                )
            }
        }
    }

    companion object {
        const val PRODUCT = "PRODUCT"

        fun newIntent(
            context: Context,
            parcelProduct: ParcelProduct,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT, parcelProduct)
            }
    }
}
