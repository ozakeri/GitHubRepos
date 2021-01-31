package gap.com.githubrepos

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.faramarzaf.sdk.af_android_sdk.core.helper.HashHelper
import com.faramarzaf.sdk.af_android_sdk.core.helper.StringHelper
import com.faramarzaf.sdk.af_android_sdk.core.util.MyPreferences
import dagger.hilt.android.AndroidEntryPoint
import gap.com.githubrepos.entitiy.SearchResponse
import gap.com.githubrepos.utils.*
import gap.com.githubrepos.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transparentToolbar(this)
        btnLogin.setOnClickListener(this)
        handleAuthResponse()
        checkUserIsAuth()
    }

    override fun onClick(view: View?) {
        val username = edt_username.text.toString().trim()
        showProgressBar(authProgressBar)

        if (checkBox.isChecked && !StringHelper.stringIsEmptyOrNull(
                edt_username.text.toString().trim()
            )
        ) {
            MyPreferences.writeString(applicationContext, KEY_USERNAME, username)
            MyPreferences.writeString(
                applicationContext,
                KEY_SESSION_ID,
                HashHelper.sha256(username)
            )
            viewModel.auth(username)
        } else if (!checkBox.isChecked && !StringHelper.stringIsEmptyOrNull(
                edt_username.text.toString().trim()
            )
        ) {
            MyPreferences.writeString(this, KEY_USERNAME, username)
            viewModel.auth(username)
        } else if (StringHelper.stringIsEmptyOrNull(edt_username.text.toString().trim())) {
            hideProgressBar(authProgressBar)
            toast("Fill fields!")
        }
    }

    private fun checkUserIsAuth() {
        if (!StringHelper.stringIsEmptyOrNull(getSessionId())) {
            toActivity(HomeActivity::class.java)
            finish()
        }
    }

    fun handleAuthResponse() {
        viewModel.loginResponse.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar(authProgressBar)
                    if (it.value.totalCount == 0) {
                        toast("User not found!")
                    } else {
                        hideProgressBar(authProgressBar)
                        saveUsefulUrls(it.value)
                        toActivity(HomeActivity::class.java)
                        finish()
                    }

                }
                is Resource.Failure -> {
                    hideProgressBar(authProgressBar)
                    if (it.isNetworkError) {
                        toast("Check your connection!")
                    }
                    toast(it.toString())
                }
            }
        })
    }

    private fun saveUsefulUrls(response: SearchResponse) {
        val username = edt_username.text.toString().trim()
        for (info in response.items) {
            if (info.login.equals(username)) {
                MyPreferences.writeString(this, KEY_AVATAR_URL, info.avatarUrl.toString())
                MyPreferences.writeString(this, KEY_HTML_URL, info.htmlUrl.toString())
            }
        }
    }

}