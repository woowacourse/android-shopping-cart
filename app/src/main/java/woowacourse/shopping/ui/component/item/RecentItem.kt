package woowacourse.shopping.ui.component.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.domain.Product

@Composable
fun RecentItem(
    product: Product,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(80.dp) // 사진 비율에 맞게 적절한 너비 지정
            .clickable { onClick(product) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductImage(
            product.imageUri,
            modifier = Modifier
                .size(80.dp)
        )
        Text(
            text = product.name,
            fontSize = 11.sp,
            color = Color.DarkGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
        )
    }
}
