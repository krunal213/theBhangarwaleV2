package com.app.thebhangarwale.custom.activity_result

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import com.app.thebhangarwale.custom.entity.VideoResponse

class BhangarwaleTakeVideo : ActivityResultContract<Uri?, VideoResponse>() {

    private val intent: Intent by lazy { Intent(MediaStore.ACTION_VIDEO_CAPTURE) }

    override fun createIntent(context: Context, input: Uri?): Intent {
        return intent.putExtra(MediaStore.EXTRA_OUTPUT, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): VideoResponse {
        return if (resultCode == Activity.RESULT_OK) {
            VideoResponse(
                resultCode,
                this.intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT)
            )
        } else {
            VideoResponse(resultCode, null)
        }
    }
}