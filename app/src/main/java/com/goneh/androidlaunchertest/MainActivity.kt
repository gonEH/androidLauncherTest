package com.goneh.androidlaunchertest

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    class MyBaseAdapter(private val context: Context,
                        private val appList: List<ResolveInfo>) : BaseAdapter() {
        override fun getCount(): Int {
            return appList.size
        }

        override fun getItem(position: Int): Any {
            return appList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val imageView: ImageView
            if (convertView == null) {
                // if it's not recycled, initialize some attributes.
                imageView = ImageView(context)
                imageView.layoutParams = GridView@AbsListView.LayoutParams(85, 85) // Since GridView doesn't have it's own implementation of LayoutParams you have to choose an implementation of its superclass AbsListView.
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.setPadding(8, 8, 8, 8)
            } else {
                imageView = convertView as ImageView
            }

            val resolveInfo = appList[position]
            imageView.setImageDrawable(resolveInfo.loadIcon(context.packageManager))

            return imageView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val intentList = getPackageManager().queryIntentActivities(intent, 0)

        gridView.adapter = MyBaseAdapter(this, intentList)
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            val clickedResolveInfo = adapterView.getItemAtPosition(i) as ResolveInfo
            val clickedActivityInfo = clickedResolveInfo.activityInfo

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.setClassName(
                    clickedActivityInfo.applicationInfo.packageName,
                    clickedActivityInfo.name)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }
}
