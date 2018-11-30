//Update your package here
package com.example.tanyongrui.advancedmovie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //register the menu from main.xml into activity_main.xml
        // since this MainActivity kotlin is for activity_main.xml
        registerForContextMenu(landingPageText) //match id of text in order for pop up to work

    }

    //once add text is press, function logic below
    override fun onContextItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == 1001) {
            //finish() //end the screen...
            // call intent to move to add Movie screen
            var myIntent = Intent(this, AddMovieActivity::class.java)
            startActivity(myIntent)
        }

        return super.onContextItemSelected(item)
    }

    //long press, the add text will display
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v?.id == R.id.landingPageText) {
            menu?.add(1,1001,1,"Add")
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu) //inflate the main.xml from menu directory

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.miRefresh) {
            landingPageText.text = "Refresheddd"
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
        }
        else if (item?.itemId == R.id.miLogoff) {
            landingPageText.text = "Logged333 off"
            Toast.makeText(this, "Loggg55ed off", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
}


