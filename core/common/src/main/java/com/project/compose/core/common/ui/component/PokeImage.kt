package com.project.compose.core.common.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.project.compose.core.common.ui.component.attr.PokeImageAttr.getImage

@Composable
fun PokeImage(
    id: Int,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = getImage(id),
        contentDescription = "Pokemon Image"
    )
}