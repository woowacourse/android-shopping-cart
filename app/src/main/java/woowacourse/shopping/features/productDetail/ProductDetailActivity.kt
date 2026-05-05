package woowacourse.shopping.features.productDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.data.DataProvider
import woowacourse.shopping.features.cart.CartActivity

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val product = intent.getParcelableExtra<ParcelProduct>("PRODUCT")!!
        val stateHolder =
            ProductDetailStateHolder(
                cartRepository = DataProvider.cartRepository,
            )

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ProductDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    productName = product.name,
                    productPrice = product.price,
                    productImageUrl = product.imageUrl,
                    onAddToCartClick = {
                        val cartIntent = Intent(this, CartActivity::class.java)
                        stateHolder.addToCart(product)
                        startActivity(cartIntent)
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
