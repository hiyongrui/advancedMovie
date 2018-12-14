package com.example.tanyongrui.advancedmovie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_edit_movie.*

class EditMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //code for back button to display, or use manifest

        editCheckBoxSuitable.setOnClickListener {
            if (editCheckBoxSuitable.isChecked) {
                editCheckBoxViolence.visibility = View.VISIBLE
                editCheckBoxLanguageUsed.visibility = View.VISIBLE
            } else {
                editCheckBoxViolence.visibility = View.GONE
                editCheckBoxLanguageUsed.visibility = View.GONE
                editCheckBoxViolence.isChecked = false
                editCheckBoxLanguageUsed.isChecked = false
            }
        }

        val receivedEditMovieObj = intent.getSerializableExtra("editMovieObj") as MovieEntity
        Log.e("received edit movie obj in edit page = " , "edit = \n" + receivedEditMovieObj.toString())
        editNameOfMovie.setText(receivedEditMovieObj.title)
        editDescription.setText(receivedEditMovieObj.overview)
        editReleaseDate.setText(receivedEditMovieObj.releaseDate)

        val list = mutableListOf(editRadioButtonEnglish,editRadioButtonChinese, editRadioButtonMalay, editRadioButtonTamil)

        for (i in 0 until list.size) {
            if (list[i].text.toString() == receivedEditMovieObj.language) {
                Log.e("radio button edit found!!", "text is " + list[i].text)
                list[i].isChecked = true
            }
        }

        if (receivedEditMovieObj.suitableAge.contains("No")) {
            editCheckBoxSuitable.isChecked = true
            editCheckBoxViolence.visibility = View.VISIBLE
            editCheckBoxLanguageUsed.visibility = View.VISIBLE
            Log.e("receive edit checked true!!", "11111111111")
            when {
                receivedEditMovieObj.suitableAge.contains("violence, language", ignoreCase = true) -> {
                    Log.e("violence language!!", "222222222")
                    editCheckBoxViolence.isChecked = true
                    editCheckBoxLanguageUsed.isChecked = true
                }
                "Violence" in receivedEditMovieObj.suitableAge -> {
                    Log.e("violence!!", "3333333333 ")
                    editCheckBoxViolence.isChecked = true
                }
                "Language" in receivedEditMovieObj.suitableAge -> {
                    Log.e("language!!", "44444444444")
                    editCheckBoxLanguageUsed.isChecked = true
                }
            }
        }

        Log.e("received edit movie object language radio button", receivedEditMovieObj.language)
        Log.e("received checkbox suitable age edits --> ", receivedEditMovieObj.suitableAge)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.save_menu, menu) //inflate the save_menu.xml from menu directory

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if ( (item?.itemId == android.R.id.home) || (item?.itemId == R.id.cancel) ) {  //code for back button to display, or use manifest
            finish() //destroy current activity, clear activity from current stack meaning go back previous
        }

        if (item?.itemId == R.id.save) {

            val validateReviewsStatus = validateReviews() //validate all fields empty/no

            if (validateReviewsStatus) {
                val radioButtonText = getRadioButtonText()
                val suitableYesOrNo = checkSuitableForChildren()

                val savingEditedMovieObj = intent.getSerializableExtra("editMovieObj") as MovieEntity
                Log.e("saving intent going back to view movie", "saving@@@@@@@@@@")
                savingEditedMovieObj.title = editNameOfMovie.text.toString()
                savingEditedMovieObj.overview = editDescription.text.toString()
                savingEditedMovieObj.releaseDate = editReleaseDate.text.toString()
                savingEditedMovieObj.language = radioButtonText
                savingEditedMovieObj.suitableAge = suitableYesOrNo
                val myIntent = Intent(this, ViewMovieActivity::class.java)
                myIntent.putExtra("callThisShit", savingEditedMovieObj) //"editedObj"
                startActivityForResult(myIntent, 222)
                //setResult(889, myIntent) // set data to be given back to viewMovie
                //finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun validateReviews(): Boolean {
        var statusOfValidation = true
        val list = mutableListOf(editNameOfMovie, editDescription, editReleaseDate)
        Log.d("validating list", " list is " + list)
        Log.d("length of list", "length/size = " + list.size)
        for (i in 0 until list.size) {
            System.out.println("list i val " + i + " value = " + list[i])
            System.out.println("text values in list : " + list[i].text)
            if (list[i].text.toString() == "") {
                System.out.println("list[i] is empty !!! " + list[i].text)
                statusOfValidation = false
                list[i].error = "Field empty"
            }
        }

        return statusOfValidation
    }


    private fun checkSuitableForChildren(): String{
        var suitableForChildren = "No"

        if (!editCheckBoxSuitable.isChecked) {
            suitableForChildren = "Yes"
        }
        else{
            if (editCheckBoxLanguageUsed.isChecked && editCheckBoxViolence.isChecked) {
                suitableForChildren = "No (Violence, Language used)"
            }
            else if (editCheckBoxViolence.isChecked) {
                suitableForChildren = "No (Violence)"
            }
            else if (editCheckBoxLanguageUsed.isChecked) {
                suitableForChildren = "No (Language Used)"
            }
        }
        return suitableForChildren
    }


    private fun getRadioButtonText() : String {
        val radioButtonText: String
        val radioButtonId: Int = editRadioGroupLanguage.checkedRadioButtonId
        Log.e("radio button id " , radioButtonId.toString())
        val radio: RadioButton = findViewById(radioButtonId)
        radioButtonText = radio.text.toString()

        return radioButtonText
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("inside on activit result EDIT MOVIE going back", "to main activity @@@@ \n" + data)

        if (resultCode == 111) {
            val rateMovieObj = data!!.getSerializableExtra("lastObject") as MovieEntity
            Log.e("going back to main from VIEW  ", "YOOOOOOOO \n " + rateMovieObj.toString())
            val sendMainIntent = Intent(this, MainActivity::class.java)
            sendMainIntent.putExtra("backToMainFromEdit",rateMovieObj)
            setResult(309,sendMainIntent)
            finish()
        }
    }
}
