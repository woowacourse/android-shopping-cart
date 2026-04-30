package woowacourse.shopping.ui.cart.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.ui.ShoppingTypography

@Composable
fun CartItemUnit(
    product: Product,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(18.dp),
    ) {
        NameAndCloseIcon(product = product, onClick = onDeleteClick)

        Spacer(Modifier.size(20.dp))

        ImageAndPrice(product)
    }
}

@Composable
private fun NameAndCloseIcon(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = product.name,
            color = Color.DarkGray,
            style = ShoppingTypography.productName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "닫기",
            modifier = Modifier.clickable(onClick = onClick),
            tint = Color.Gray,
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ImageAndPrice(
    product: Product,
    modifier: Modifier = Modifier,
) {
    val price = product.price.value
    val formatted = String.format("%,d", price)

    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "이미지",
            modifier =
                Modifier
                    .width(136.dp)
                    .height(72.dp),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = "$formatted 원",
            color = Color.DarkGray,
            style = ShoppingTypography.productPrice,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Preview(showBackground = true, name = "카트 아이템 유닛")
@Composable
private fun CartItemUnitPreview() {
    CartItemUnit(product = InMemoryProductRepository.APPLE, onDeleteClick = {})
}

@Preview(showBackground = true, name = "이름과 닫기아이콘")
@Composable
private fun NameAndCloseIconPreview() {
    NameAndCloseIcon(product = InMemoryProductRepository.APPLE, onClick = {})
}

@Preview(showBackground = true, name = "사진과 금액")
@Composable
private fun ImageAndPricePreview() {
    ImageAndPrice(InMemoryProductRepository.APPLE)
}
