package com.kuluruvineeth.socialnetwork.core.presentation.util

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.inputmethod.InputMethodManager


fun Context.showKeyboard(){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(null, InputMethodManager.SHOW_FORCED)
}