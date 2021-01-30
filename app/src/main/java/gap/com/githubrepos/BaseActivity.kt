package gap.com.githubrepos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.faramarzaf.sdk.af_android_sdk.core.helper.ScreenHelper
import com.faramarzaf.sdk.af_android_sdk.core.util.MyPreferences
import gap.com.githubrepos.utils.KEY_SESSION_ID

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showProgressBar(pg: ProgressBar) {
        pg.visibility = View.VISIBLE
    }

    fun hideProgressBar(pg: ProgressBar) {
        pg.visibility = View.GONE
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun getSessionId(): String {
        return MyPreferences.readString(this, KEY_SESSION_ID, "")
    }


    open fun toActivity(classOf: Class<*>) {
        startActivity(Intent(applicationContext, classOf))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun transparentToolbar(activity: Activity) {
        ScreenHelper.hideToolbar(activity)
    }
}