package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.AppContainer
import woowacourse.shopping.R
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.ui.theme.buttonColor
import woowacourse.shopping.ui.theme.topAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListTopAppBar(
    totalProductQuantity:Int,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.shopping),
            )
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shopping_cart_icon),
                    contentDescription = stringResource(R.string.shopping_cart),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onClick() }
                )
                if (totalProductQuantity > 0) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp)
                            .background(buttonColor)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = totalProductQuantity.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.W500,
                        )
                    }
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
        totalProductQuantity = 2,
        onClick = {},
    )
}
