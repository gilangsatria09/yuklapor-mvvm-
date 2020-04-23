package com.gproduction.yuklapor.ui.report

import android.os.Bundle
import android.view.MenuItem
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.ui.BaseActivity
import com.gproduction.yuklapor.ui.report.fragment.ReportFragment
import kotlinx.android.synthetic.main.toolbar.toolbar

class ReportActivty : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_activty)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.report)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addFragment(ReportFragment.newInstance())
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            1 -> finish()
            else -> super.onBackPressed()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()

        return super.onOptionsItemSelected(item)
    }
}
