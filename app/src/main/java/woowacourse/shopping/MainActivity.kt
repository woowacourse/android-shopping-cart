package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.ui.component.screen.CatalogScreen
import woowacourse.shopping.ui.stateholder.retainCartStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AndroidshoppingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val stateHolder = retainCartStateHolder()
                    CatalogScreen(
                        catalog = stateHolder.catalog,
                        onItemClick = { id ->
                            val productDetailIntent =
                                Intent(this, ProductDetailActivity::class.java)
                            productDetailIntent.putExtra("id", id.toString())
                            startActivity(productDetailIntent)
                        },
                        onCartClick = {
                            val cartIntent = Intent(this, CartActivity::class.java)
                            startActivity(cartIntent)
                        },
                        onLoadClick = { stateHolder.onLoadClick() },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
