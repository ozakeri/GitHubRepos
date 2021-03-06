package gap.com.githubrepos.utils

import android.os.Bundle
import android.view.View
import app.android.githubservice.interfaces.GlobalBottomSheetCallBack
import com.faramarzaf.sdk.af_android_sdk.core.interfaces.DoGuardTask
import com.faramarzaf.sdk.af_android_sdk.core.util.ClickGuard
import gap.com.githubrepos.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

open class BottomSheetProfile : BaseBottomSheet(), DoGuardTask {

    override val fragmentLayout: Int
        get() = R.layout.fragment_bottom_sheet

    private var globalBottomSheetCallBack: GlobalBottomSheetCallBack? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ClickGuard.guardView(imgLogout, DELAY_GUARD_CLICK, this)
    }

    override fun onGuard(view: View) {
        when (view.id) {
            R.id.imgLogout -> {
                globalBottomSheetCallBack!!.onLogoutClick()
            }
        }
    }

    fun setOnBottomSheetClickListener(listener: (GlobalBottomSheetCallBack)) {
        globalBottomSheetCallBack = listener
    }
}