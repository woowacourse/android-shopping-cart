@file:Suppress("FunctionName")

package woowacourse.shopping

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidShoppingTheme {
                Scaffold(
                    topBar = this::TopBar,
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ProductItem(
                        product =
                            Product(
                                id = 1,
                                title = "동원 스위트콘",
                                price = 99_800,
                                imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
                            ),
                        modifier =
                            Modifier.padding(innerPadding).clickable(onClick = {
                                startActivity(Intent(this, DetailProductActivity::class.java))
                            }),
                    )
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        TopAppBar(
            title = { Text(text = "Shopping") },
            actions = {
                IconButton(onClick = {
                    startActivity(Intent(this@MainActivity, ShoppingCartActivity::class.java))
                }) {
                    Image(
                        painter = painterResource(R.drawable.shopping_cart_icon),
                        contentDescription = "장바구니 아이콘",
                        modifier = Modifier.size(22.dp),
                    )
                }
            },
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
        )
    }
}
