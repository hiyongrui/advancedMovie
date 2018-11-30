package com.example.tanyongrui.advancedmovie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_movie.*

class AddMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        //registerForContextMenu(clearEntries)
        rbtnButton1.isChecked = true

        checkBoxSuitable.setOnClickListener {
            if (checkBoxSuitable.isChecked) {
                checkBoxViolence.visibility = View.VISIBLE
                checkBoxLanguageUsed.visibility = View.VISIBLE
            } else {
                checkBoxViolence.visibility = View.GONE
                checkBoxLanguageUsed.visibility = View.GONE
                checkBoxViolence.isChecked = false
                checkBoxLanguageUsed.isChecked = false
            }
        }

    } //end of override fun create method

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_menu, menu) //inflate the main.xml from menu directory

        return super.onCreateOptionsMenu(menu)
    }

    //when clear entries is clicked
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.clearEntries) {
            Toast.makeText(this, "Clear entries", Toast.LENGTH_SHORT).show()

            val listOfText = mutableListOf(nameOfMovie, description, releaseDate)
            val listOfCheckBox = mutableListOf(checkBoxSuitable, checkBoxViolence, checkBoxLanguageUsed)
            for (i in 0 until listOfText.size) {
                listOfText[i].setText("")
                listOfCheckBox[i].isChecked = false
            }
            val radioButtonId: Int = radioGroupLanguage.checkedRadioButtonId
            val radio: RadioButton = findViewById(radioButtonId)
            radio.isChecked = false
            radioGroupLanguage.check(R.id.rbtnButton1)
            Log.e("radio button id uncheck ---> ", "radio id " + radioButtonId)

            checkBoxViolence.visibility = View.GONE
            checkBoxLanguageUsed.visibility = View.GONE
        }

        if (item?.itemId == R.id.add) {
            val statusOfValidation = validationDone() //validate all fields empty/no
            System.out.println("validation done fail/pass " + statusOfValidation)
            if (statusOfValidation) {
                val radioButtonText = getRadioButtonText()
                val suitableYesOrNo = checkSuitableForChildren()

                //call movie entity class, create object, put in the values and
                // pass the object into the next activities through intent
                val movieEntityObject = MovieEntity()
                movieEntityObject.title = nameOfMovie.text.toString()
                movieEntityObject.overview = description.text.toString()
                movieEntityObject.releaseDate = releaseDate.text.toString()
                movieEntityObject.language = radioButtonText
                movieEntityObject.suitableAge = suitableYesOrNo

                val myIntent = Intent(this, ViewMovieActivity::class.java)
                myIntent.putExtra("callThisShit", movieEntityObject)
                startActivity(myIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun checkSuitableForChildren(): String{
        var suitableForChildren = "No"

        if (!checkBoxSuitable.isChecked) {
            suitableForChildren = "Yes"
        }
        else{
            if (checkBoxLanguageUsed.isChecked && checkBoxViolence.isChecked) {
                suitableForChildren = "No (Violence, Language used)"
            }
            else if (checkBoxViolence.isChecked) {
                suitableForChildren = "No (Violence)"
            }
            else if (checkBoxLanguageUsed.isChecked) {
                suitableForChildren = "No (Language Used)"
            }
        }
        return suitableForChildren
    }

    private fun getRadioButtonText() : String {
        val radioButtonText: String
        val radioButtonId: Int = radioGroupLanguage.checkedRadioButtonId
        Log.e("radio button id " , radioButtonId.toString())
        val radio: RadioButton = findViewById(radioButtonId)
        radioButtonText = radio.text.toString()

        return radioButtonText
    }

    private fun validationDone(): Boolean {
        var statusOfValidation = true
        val list = mutableListOf(nameOfMovie,description, releaseDate)
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


}
