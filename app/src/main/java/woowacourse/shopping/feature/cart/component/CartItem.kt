package woowacourse.shopping.feature.cart.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.core.designsystem.component.NetworkImage

@Composable
fun CartItem(
    productName: String,
    productUrl: String,
    price: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = Dp.Hairline,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 18.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = productName,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_close_16),
                tint = Color.LightGray,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onClick)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            NetworkImage(
                imageUrl = productUrl,
                modifier = Modifier
                    .widthIn(max = 136.dp)
                    .aspectRatio(136f / 72f)
            )

            Text(
                text = price,
                color = Color.Black,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartItemPreview() {
    CartItem(
        productName = "리자몽",
        productUrl = "",
        price = "10,000원",
        onClick = {}
    )
}
