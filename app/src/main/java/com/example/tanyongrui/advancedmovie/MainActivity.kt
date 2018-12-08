//Update your package here
package com.example.tanyongrui.advancedmovie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //TODO 1 :
    //Override the following methods :
    // - onCreateOptionsMenu
    // - onOptionsItemSelected
    // - onCreateContextMenu
    // - onContextItemSelected
    //TODO 2:
    //Create new menu resource file, main.xml
    //TODO 3:
    // Update menu resource, main.xml with the appropriate menu items and submenu
    //Refresh menu item[always show in App bar]
    //Search menu item[always show in App bar]
    //Data [Sub-menu]
    //Import[Sub-menu item of Data]
    //Clear[Sub-menu item of Data]
    //Settings menu item [never show in App bar]
    //Logoff menu item [never show in App bar]
    //TODO 4:
    // In onCreateOptionsMenu, Inflate main.xml using menuInflater
    //TODO 5:
    //In onOptionsItemSelected,
    //  - set tvDemo text to "Refreshed" and display "Refresh" using Toast when Refresh menu item is clicked
    //  - set tvDemo text to "Logged off" and display "Logged off" using Toast when Log off menu item is clicked
    //TODO 6:
    //In onCreate, register context menu to tvDemo
    //TODO 7:
    //In onCreateContextMenu,
    // - check to ensure if tvDemo is be triggered, Add menu item "Goodbye" and itemId 1001
    //TODO 8:
    //In onContextItemSelected,
    // - when the itemId 1001 is selected, end the activity by calling the finish()

    // lateinit defer initialisation until the first time they are used,
    // inform compiler this variable assigned later, free compiler from making sure this variable gets initialized
    lateinit var listView: ListView

    var personArray: MutableList<String> = ArrayList()
    //var listGlobal = mutableListOf<Persons>()
    var listGlobal = mutableListOf<MovieEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("on create-----","creating home page logggggggggg")
        //register the menu from main.xml into activity_main.xml
        // since this MainActivity kotlin is for activity_main.xml
        //registerForContextMenu(landingPageText) //match id of text in order for pop up to work
        registerForContextMenu(listViewOfMovies)

        //listView = findViewById(R.id.listViewOfMovies)
        //var personArray = arrayOf("oaoa","sax","noted","k")

        /*
        personArray.add("avengers")
        personArray.add("ok2nd")
        personArray.add("noted")
        personArray.add("hello")
        
        val newAdapter = ArrayAdapter<String>(
            this,
                    android.R.layout.simple_list_item_1,
                    personArray
        )

        Log.e("array person --- ", "==========  " + personArray)
        listViewOfMovies.adapter = newAdapter
        */

    }


    //once add popup text is press, function logic below
    override fun onContextItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == 1001) {
            Log.e("system error no item", "no item")
            //finish() //end the screen...
            // call intent to move to add Movie screen
            var myIntent = Intent(this, AddMovieActivity::class.java)
            startActivity(myIntent)
        }

        //Todo 777 once long press, pop up "Edit" and edit this particular movie object selected
        //var selectedItemNo = item!!.itemId

        val info = item!!.menuInfo as AdapterView.AdapterContextMenuInfo
        val listPosition = info.position
        //val name = personArray[listPosition]
        val name = listGlobal[listPosition]
        Toast.makeText(this,"hi +  ${name.title}", Toast.LENGTH_LONG).show()
        Log.e("selected adapter item !!! ", "item object =\n" + name)

        val editIntent = Intent(this, EditMovieActivity::class.java)
        editIntent.putExtra("editMovieObj", name)
        startActivity(editIntent)
        Log.e("from main activity passing over edit object...", "editing@@@@@@@@@@")
        return super.onContextItemSelected(item)
    }

    //long press, the add text will display
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        Log.e("system 11111111 no item", "no item")

        if (v?.id == R.id.listViewOfMovies) {
            Log.e("list selected edit", "edit@@@m")
            menu?.add(1, v.id,2,"Edit")
        } //v.id or 1001 = itemid

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu) //inflate the main.xml from menu directory

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //TODO 888 put "Add" at the top of the menu, start intent to add movie page:
        //TODO once added movie, object added to global array, pass back from view movie
        if (item?.itemId == R.id.addMovieFromHome) {
            var myIntent = Intent(this, AddMovieActivity::class.java)
            //startActivity(myIntent)
            startActivityForResult(myIntent,10)
        }

        return super.onOptionsItemSelected(item)
    }

    //TODO LAST receive object from view movie (3rd screen), set to custom adapter, global array add
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("\n\n finally back from to 0", "CORRECT@@@@@@@@@@@@@@ \n" + data)

        if (resultCode == 223 && requestCode == 10) {
            val backOBJ = data!!.getSerializableExtra("backToMain") as MovieEntity
            Log.e("1st screen data from view movie... ",
                "data passed back from last screen@@@@ \n" + backOBJ.toString())
        }

    }


}


