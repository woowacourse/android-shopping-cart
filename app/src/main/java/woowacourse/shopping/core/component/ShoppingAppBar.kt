package woowacourse.shopping.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.ui.theme.Gray50

@Composable
fun ShoppingAppBar(
    contents: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(Gray50)
                .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        contents()
    }
}

@Preview
@Composable
private fun ShoppingAppBarPreview() {
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
                modifier = Modifier.size(24.dp),
            )
        },
    )
}
