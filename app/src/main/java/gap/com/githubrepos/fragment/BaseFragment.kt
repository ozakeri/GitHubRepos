package gap.com.githubrepos.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.faramarzaf.sdk.af_android_sdk.core.helper.ScreenHelper
import com.faramarzaf.sdk.af_android_sdk.core.ui.dialog.ProgressDialogCustom
import gap.com.githubrepos.R

abstract class BaseFragment : Fragment() {

    protected var mProgressDialog: ProgressDialogCustom? = null
    protected abstract val getFragmentLayout: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(getFragmentLayout, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let { transparentToolbar(it) }
    }

    fun transparentToolbar(activity: Activity) {
        ScreenHelper.hideToolbar(activity)
    }

    open fun toActivity(activity: Activity?, classOf: Class<*>) {
        startActivity(Intent(activity, classOf))
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun toast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    fun setRecyclerviewDivider(context: Context, recyclerView: RecyclerView, resId: Int) {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context, resId)!!)
        recyclerView.addItemDecoration(divider)
    }

    fun showProgressBar(pg: ProgressBar) {
        pg.visibility = View.VISIBLE
    }

    fun hideProgressBar(pg: ProgressBar) {
        pg.visibility = View.GONE
    }

    protected fun showProgressDialog() {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialogCustom(
                    activity,
                    R.layout.layout_dialog_progress,
                    R.drawable.ic_launcher_background, false
                )
            }
            mProgressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun hideProgressDialog() {
        try {
            if (activity != null && mProgressDialog != null) {
                mProgressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}