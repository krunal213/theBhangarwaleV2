package com.app.thebhangarwale.custom.activity_result

import androidx.activity.result.contract.ActivityResultContract
import android.content.Intent
import android.provider.MediaStore
import android.app.Activity
import android.content.Context
import android.net.Uri
import com.app.thebhangarwale.custom.entity.ImageResponse

class BhangarwaleTakePicture : ActivityResultContract<Uri?, ImageResponse>() {

    private val intent: Intent by lazy { Intent(MediaStore.ACTION_IMAGE_CAPTURE) }

    override fun createIntent(context: Context, input: Uri?): Intent {
        return intent.putExtra(MediaStore.EXTRA_OUTPUT, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ImageResponse {
        return if (resultCode == Activity.RESULT_OK) {
            ImageResponse(
                resultCode,
                this.intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT)
            )
        } else {
            ImageResponse(resultCode, null)
        }
    }
}