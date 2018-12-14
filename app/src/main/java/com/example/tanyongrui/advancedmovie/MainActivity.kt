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
    var titleToChange = ""
    var editMovieObj = MovieEntity()

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
        listViewOfMovies.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var item = listGlobal[position]
            Log.e("clicked list item --> ", "item value \n" + item.toString())
            var startViewIntent = Intent(this, ViewMovieActivity::class.java);
            startViewIntent.putExtra("callThisShit", item)
            //startActivityForResult(startViewIntent, 10);
            startActivityForResult(startViewIntent, 3)
            Toast.makeText(this,"hi " + item.title, Toast.LENGTH_LONG).show()
        }
    } //end of OnCreate()


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
        titleToChange = name.title
        val editIntent = Intent(this, EditMovieActivity::class.java)
        editIntent.putExtra("editMovieObj", name)
        startActivityForResult(editIntent, 222)
        Log.e("from main activity passing over edit object...", "editing@@@@@@@@@@")
        return super.onContextItemSelected(item)
    }


    //long press, the add text will display
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        Log.e("system 11111111 no item", "no item")

        if (v?.id == R.id.listViewOfMovies) {
            Log.e("list selected edit", "edit@@@m")
            menu?.add(1, v.id,1,"Edit")
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
            //editMovieObj = backOBJ
            //TODO 666: once added movie object, add object to array to display on adapter
            // TODO 999: other than movie object, need image? have to use custom adapter, or override methods... hardest

            listView = findViewById(R.id.listViewOfMovies)
            //TODO object added below should be in onActivityResult
            /*
            listGlobal.add(Persons("Peter","Tan","111", R.drawable.moviepicture))
            listGlobal.add(Persons("Nihao ","Kool","222", R.drawable.moviepicture))
            listGlobal.add(Persons("John","Doe","333", R.drawable.moviepicture))
            */
            //listGlobal.add(MovieEntity("Peter","Tan","111","hello","lol"))
            listGlobal.add(backOBJ)
            for (i in 0 until listGlobal.size) {
                Log.e("list", "persons dynamic below\n name = " + listGlobal[i].title)
            }

            val adapter = MyListAdapter(this, R.layout.my_list_item, listGlobal) //params = context, layout id, list of data
            // maps the array of object to the custom adapter list view
            listView.adapter = adapter
        }

        if (requestCode == 222 && resultCode == 309) {
            Log.e("hello 222 111 its working", "data-???")
            val savedOBJ = data!!.getSerializableExtra("backToMainFromEdit") as MovieEntity
            Log.e("finally back from edit???????. ",
                "saved object is\n" + savedOBJ.toString())

            listView = findViewById(R.id.listViewOfMovies)
            //TODO object added below should be in onActivityResult
            for (i in 0 until listGlobal.size) {
                Log.e("before change", "object below\n = " + listGlobal[i].title)
            }
            for (i in 0 until listGlobal.size) {
                Log.e("object list", listGlobal.toString())
                if (listGlobal[i].title == titleToChange) {
                    listGlobal[i] = savedOBJ
                }
            }
            for (i in 0 until listGlobal.size) {
                Log.e("after change", "bobject below\n @ " + listGlobal[i].title)
            }

            val adapter = MyListAdapter(this, R.layout.my_list_item, listGlobal) //params = context, layout id, list of data
            // maps the array of object to the custom adapter list view
            listView.adapter = adapter
        }
        if (requestCode == 837 && resultCode == 908) {
            Log.e("837 908", "COMING BACK LIST ITEM ");
            var viewUpdated = intent.getSerializableExtra("okObject") as MovieEntity
            Log.e("view updated -- " , "--- \n" + viewUpdated.toString())
        }

        if (resultCode == 111) {
            val fuckObj = data!!.getSerializableExtra("lastObject") as MovieEntity
            Log.e("gFUCK UR MOTHE@@@@@@ ", "YOOOOOOOO \n " + fuckObj.toString())
            for (i in 0 until listGlobal.size) {
                if (listGlobal[i].title == fuckObj.title) {
                    listGlobal[i] = fuckObj
                }
            }
            Log.e("GLOBAL FUCK DOG ARRAY", "OOTOTOT \n " + listGlobal[0])
        }
    } //end of onActivityResult


}


