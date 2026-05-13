package woowacourse.shopping.presentation.shopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.presentation.theme.topAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListTopAppBar(
    totalQuantity: Int,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Shopping",
            )
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 16.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shopping_cart_icon),
                    contentDescription = "shoppingCart",
                    modifier =
                        Modifier
                            .size(24.dp)
                            .clickable { onClick() },
                )
                if (totalQuantity > 0) {
                    QuantityLabel(
                        quantity = totalQuantity,
                        modifier =
                            Modifier
                                .padding(start = 4.dp)
                                .size(24.dp),
                    )
                }
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = topAppBarColor,
                titleContentColor = Color.White,
            ),
    )
}

@Preview
@Composable
private fun ProductListTopAppBarPreview() {
    ProductListTopAppBar(
        totalQuantity = 0,
        onClick = {},
    )
}
