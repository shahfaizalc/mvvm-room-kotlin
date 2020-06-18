package com.faizal.android.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faizal.android.database.R
import com.faizal.android.database.databinding.ActivityDeatailBinding
import com.faizal.android.injection.App
import com.faizal.android.injection.module.ConstantsModule
import com.faizal.android.model.ClubsModel
import com.faizal.android.utils.imageLoad
import com.faizal.android.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.customappbar.view.*
import javax.inject.Inject

class DetailedActivity : AppCompatActivity() {
    private var binding: ActivityDeatailBinding? = null
    @Inject
    lateinit var constantsModule: ConstantsModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deatail)
        setSupportActionBar(binding!!.included.toolbar);
        val navigationLeft = ContextCompat.getDrawable(getApplicationContext(), R.drawable.arrow_left);
        binding!!.included.toolbar.navigationIcon = navigationLeft
        binding!!.included.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        // Get a new or existing ViewModel from the ViewModelProvider.
        val mClubsViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding!!.mainData = mClubsViewModel
        val str: String = intent.extras!!.get(constantsModule.bundleKeyClubName()) as String
        binding!!.mainData!!.fetchClub(str)

        mClubsViewModel.getClubs.observe(this, Observer { clubsModel: ClubsModel ->
            updateUIOnResult(clubsModel)
        })
    }

    private fun updateUIOnResult(clubsModel: ClubsModel) {
        binding!!.clubCountry.text = clubsModel.country;
        binding!!.included.toolbar.title = clubsModel.name
        val str = SpannableString(String.format(getString(R.string.detailView),
                clubsModel.name, clubsModel.european_titles))
        str.setSpan(StyleSpan(Typeface.BOLD), 0, clubsModel.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding!!.description.setText(str)

      imageLoad(binding!!.clubLogo, clubsModel.image)

    }

}