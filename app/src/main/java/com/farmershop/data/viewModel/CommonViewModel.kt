package com.farmershop.data.viewModel

import com.exfactor.appsdk.AppSession
import com.farmershop.data.`interface`.BaseInterface
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class CommonViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var baseInterface: BaseInterface
    val mContext: Context = getApplication<Application>().applicationContext
    val user_id = AppSession.getInstance(mContext).getUserId().toString()
    var subject: String = ""
    var feedback_message: String = ""
    var feedback_id="0"

}