package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductDetailHeader(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .padding(20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "닫기",
            modifier = Modifier.clickable(onClick = onCloseClick),
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailHeaderPreview() {
    ProductDetailHeader(onCloseClick = {})
}
