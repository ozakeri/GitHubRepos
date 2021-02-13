package gap.com.githubrepos.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.android.githubservice.interfaces.GlobalBottomSheetCallBack
import com.faramarzaf.sdk.af_android_sdk.core.helper.GlideHelper
import com.faramarzaf.sdk.af_android_sdk.core.interfaces.DialogCallback
import com.faramarzaf.sdk.af_android_sdk.core.interfaces.DoGuardTask
import com.faramarzaf.sdk.af_android_sdk.core.ui.dialog.PublicDialog
import com.faramarzaf.sdk.af_android_sdk.core.util.ClickGuard
import com.faramarzaf.sdk.af_android_sdk.core.util.MyPreferences
import dagger.hilt.android.AndroidEntryPoint
import gap.com.githubrepos.LoginActivity
import gap.com.githubrepos.R
import gap.com.githubrepos.adapter.ViewPagerProfileAdapter
import gap.com.githubrepos.utils.*
import gap.com.githubrepos.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment() , DoGuardTask {

    private lateinit var searchViewModel: SearchViewModel
    private val bottomSheetTheme = BottomSheetProfile()

    override val getFragmentLayout: Int
        get() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerOverview.adapter = ViewPagerProfileAdapter(requireActivity().supportFragmentManager)
        tabLayoutOverview.setupWithViewPager(viewPagerOverview)
        fillProfileInfo()
        bottomSheetOperations()
        ClickGuard.guardView(imgSetting, 800, this)
    }

    private fun bottomSheetOperations() {
        bottomSheetTheme.setOnBottomSheetClickListener(object : GlobalBottomSheetCallBack {
            override fun onLogoutClick() {
                openLogoutDialog()
            }
        })
    }

    private fun openLogoutDialog() {
        PublicDialog.yesNoDialog(requireContext(), getString(R.string.logout_title), getString(R.string.msg_dialog_logout)
            , getString(R.string.yes), getString(R.string.no), R.drawable.ic_logout, object :
                DialogCallback {
                override fun onNegativeButtonClicked() {
                    return
                }

                override fun onPositiveButtonClicked() {
                    logout()
                }
            })
    }

    fun logout(){
        searchViewModel.deleteAll()
        MyPreferences.clearAll(requireContext())
        toActivity(activity, LoginActivity::class.java)
        requireActivity().finish()
    }

    private fun fillProfileInfo() {
        GlideHelper.circularImage(requireContext(), MyPreferences.readString(requireContext(), KEY_AVATAR_URL, ""), avatarProfile)
        textUserNameProfile.text = MyPreferences.readString(requireContext(), KEY_USERNAME, "")
        textRepositoryProfile.text = MyPreferences.readString(requireContext(), KEY_SIZE_LIST_REPO, "")
        textFollowersProfile.text = MyPreferences.readString(requireContext(), KEY_NUMBER_FOLLOWERS, "")
        textFollowingProfile.text = MyPreferences.readString(requireContext(), KEY_NUMBER_FOLLOWING, "")
    }

    override fun onGuard(view: View) {
        bottomSheetTheme.show(requireActivity().supportFragmentManager, TAG_BOTTOM_SHEET)
    }
}