package dev.tavieto.hearthstone.core.uikit.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun GlideImageSafePreview(
    imageModel: Any?,
    preview: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    placeHolder: Any? = null,
    error: Any? = null,
    @DrawableRes previewPlaceholder: Int = 0,
) {
    val developerMode = LocalInspectionMode.current

    if (developerMode) {
        preview()
    } else {
        GlideImage(
            imageModel = imageModel,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            contentDescription = contentDescription,
            placeHolder = placeHolder,
            error = error,
            previewPlaceholder = previewPlaceholder
        )
    }
}
