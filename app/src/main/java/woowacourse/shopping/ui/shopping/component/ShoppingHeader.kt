package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.ShoppingTypography

@Composable
fun ShoppingHeader(
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.DarkGray)
                .padding(start = 26.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.shopping_title),
            color = Color.White,
            style = ShoppingTypography.titleMedium,
        )

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = stringResource(R.string.content_description_cart),
            modifier = Modifier.clickable(onClick = onCartClick),
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingHeaderPreview() {
    ShoppingHeader(onCartClick = {})
}
