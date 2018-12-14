package com.example.tanyongrui.advancedmovie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_rate_movie.*


class RateMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //code for back button to display, or use manifest

        val retrieveRateMovieObj = intent.getSerializableExtra("rateMovieObj") as MovieEntity
        enterReviewText.append(retrieveRateMovieObj.title)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // use this instead, manifest cannot, manifest will not be able to reload the object from 1st activity
        if (item?.itemId == android.R.id.home) {  //code for back button to display, or use manifest
            finish() //destroy current activity, clear activity from current stack meaning go back previous
        }

        if (item?.itemId == R.id.submit) {
            val validateReviewsStatus = validateReviews()

            if (validateReviewsStatus) {
                Log.e("submitting intent now back to view movie", "submitting@@@@@@@@@@")
                val retrieveRateMovieObj = intent.getSerializableExtra("rateMovieObj") as MovieEntity
                retrieveRateMovieObj.reviewNoOfStars = ratingStars.rating.toString()
                retrieveRateMovieObj.reviewDescription = shareYourView.text.toString()
                val myIntent = Intent(this, ViewMovieActivity::class.java)
                myIntent.putExtra("doneRateMovieObj", retrieveRateMovieObj)
                //startActivity(myIntent)
                setResult(999, myIntent) // set data to be given back to viewMovie
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.submit_menu, menu) //inflate the submit_menu.xml rom menu directory

        return super.onCreateOptionsMenu(menu)
    }


    private fun validateReviews(): Boolean {
        var checkValidationReviews = true
        if (shareYourView.text.toString() == "") {
            shareYourView.error = "Please share your view"
            checkValidationReviews = false
        }
        if (ratingStars.rating == 0.0f) {
            val inflater = layoutInflater

            val toastCustomisedLayout = inflater.inflate(R.layout.customtoast, null)
            val toastCustomised = toastCustomisedLayout.findViewById(R.id.toastDesign) as TextView
            toastCustomised.width = 6000
            toastCustomised.height = 300
            toastCustomised.text = "Select a rating!!!"
            toastCustomised.textSize = 39F
            val toast = Toast(this)
            toast.duration = Toast.LENGTH_LONG
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.view = toastCustomisedLayout
            toast.show()

            checkValidationReviews = false
        }
        return checkValidationReviews
    }
}
