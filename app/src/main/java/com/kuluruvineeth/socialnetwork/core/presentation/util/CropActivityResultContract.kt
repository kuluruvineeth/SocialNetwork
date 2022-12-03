package com.kuluruvineeth.socialnetwork.core.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yalantis.ucrop.UCrop
import androidx.activity.result.contract.ActivityResultContract
import com.kuluruvineeth.socialnetwork.core.domain.util.getFileName
import com.yalantis.ucrop.UCrop.RESULT_ERROR
import java.io.File

class CropActivityResultContract: ActivityResultContract<Uri,Uri?>() {
    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(
            input,
            Uri.fromFile(
                File(
                    context.cacheDir,
                    context.contentResolver.getFileName(input)
                )
            )
        )
            .withAspectRatio(16f,9f)
            .getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if(intent == null){
            return null
        }
        if(resultCode == RESULT_ERROR){
            val error = UCrop.getError(intent)
            error?.printStackTrace()
        }
        return UCrop.getOutput(intent)
    }

}