package com.example.tanyongrui.advancedmovie

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_view_movie.*

class ViewMovieActivity : AppCompatActivity() {

    var receivedMovieObj = MovieEntity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        registerForContextMenu(reviewText)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //code for back button to display, or use manifest

        /*
        val p = MovieEntity()
        //p.title = "title of the movie...."
        titleMovieText.text = p.title
        overviewText.text = p.overview
        languageText.text = p.language
        releaseDateText.text = p.releaseDate
        if (p.suitableAge == true) {
            suitableAgeText.text = "Yes"
        }
        else{
            suitableAgeText.text = "No"
        }
        */
        receivedMovieObj = intent.getSerializableExtra("callThisShit") as MovieEntity
        titleMovieText.text = receivedMovieObj.title
        overviewText.text = receivedMovieObj.overview
        languageText.text = receivedMovieObj.language
        releaseDateText.text = receivedMovieObj.releaseDate
        suitableAgeText.text = receivedMovieObj.suitableAge

        Log.e("---------below is movie entity in view movie ----- ", "------------")
        Log.e("",receivedMovieObj.toString())
        Log.e("title of movie", titleMovieText.text.toString())
        Log.e("overview of movie ", overviewText.text.toString())
        Log.e("language used ", languageText.text.toString())
        Log.e("release date", releaseDateText.text.toString())
        Log.e("suitable age", suitableAgeText.text.toString())

    } //end of onCreate()


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.edit_menu, menu) //inflate the edit_menu.xml from menu directory

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        //TODO last, once if item?.ItemId == press back button R.id.home, put object into intent
        // TODO set result back to main activity page, call finish() and add to global array in main
        Log.e("on option item seleced!!", "!!!!!!!!!!")
        if (item?.itemId == android.R.id.home) {
            Log.e("on option selected item HOME!!", "22222222")
            Log.e("global variable object string", receivedMovieObj.toString())
            //val sendToMain = intent.getSerializableExtra("callThisShit") as MovieEntity //callthisShit
            val sendMainIntent = Intent(this, AddMovieActivity::class.java)
            sendMainIntent.putExtra("lastObject",receivedMovieObj) //editMovieObjPass
            setResult(111,sendMainIntent)
            finish() //back to add activity, which will finish() to main activity page
        }

        if (item?.itemId == R.id.edit) {
            val editMovieObj = intent.getSerializableExtra("callThisShit") as MovieEntity
            val editIntent = Intent(this, EditMovieActivity::class.java)
            editIntent.putExtra("editMovieObj", editMovieObj)
            startActivityForResult(editIntent, 888)
            Log.e("starting edit intent passing over edit object...", "editing-----------------")
        }

        return super.onOptionsItemSelected(item)
    }

    //once add review text is press, function logic below
    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 1001) {
            //finish() //end the screen...
            // call intent to move to rate Movie screen
            val rateMovieObj = intent.getSerializableExtra("callThisShit") as MovieEntity
            val rateIntent = Intent(this, RateMovieActivity::class.java)
            rateIntent.putExtra("rateMovieObj", rateMovieObj)
            startActivityForResult(rateIntent, 666) // this will return the data from rate movie
            Log.e("starting intent now 666", "started@@@@@@@@@@")
        }
        return super.onContextItemSelected(item)
    }

    //long press, the add review text will display
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == R.id.reviewText) {
            menu?.add(1,1001,1,"Add Review")
        }
    }

    //override this to get data set from rateMovie, rather than onCreate() the data will be gone/error
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("getting back intent now 666", "getting@@@@@@@@@@")
        // rate movie
        if (resultCode == 999 && requestCode == 666) {
            Log.e("inside 999 and 666", "inside@@@@@@@@@@")
            val rateMovieObj = data!!.getSerializableExtra("doneRateMovieObj") as MovieEntity
            ratingStarsNotVisible.visibility = View.VISIBLE
            ratingStarsNotVisible.rating = rateMovieObj.reviewNoOfStars.toFloat()
            reviewText.text = rateMovieObj.reviewDescription

            Log.e("rating stars movie object received", rateMovieObj.toString())

        }

        /*
        // edit movie
        if (resultCode == 889 && requestCode == 888) {
            Log.e("inside 889 and 888", "inside===============")
            editMovieObjPass = data!!.getSerializableExtra("editedObj") as MovieEntity
            titleMovieText.text = editMovieObjPass.title
            overviewText.text = editMovieObjPass.overview
            releaseDateText.text = editMovieObjPass.releaseDate
            languageText.text = editMovieObjPass.language
            suitableAgeText.text = editMovieObjPass.suitableAge
        }
        */
    }

}
