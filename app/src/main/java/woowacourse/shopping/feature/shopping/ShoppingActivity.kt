package woowacourse.shopping.feature.shopping

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import woowacourse.shopping.core.component.ShoppingAppBar
import woowacourse.shopping.core.data.ProductData
import woowacourse.shopping.core.model.Product
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.shopping.ui.ShoppingContents
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                ShoppingScreen(
                    data = ProductData.products,
                )
            }
        }
    }
}

@Composable
fun ShoppingScreen(
    data: ImmutableList<Product>,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    Scaffold(
        topBar = {
            ShoppingAppBar(
                contents = {
                    Text(
                        text = "Shopping",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "쇼핑 카트",
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clickable {
                                    val intent = Intent(activity, CartActivity::class.java)
                                    activity?.startActivity(intent)
                                },
                    )
                },
                modifier = modifier.fillMaxWidth(),
            )
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        ShoppingContents(
            products = data,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
