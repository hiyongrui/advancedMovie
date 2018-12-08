package com.example.tanyongrui.advancedmovie

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyListAdapter(var mCtx:Context, var resource:Int, var items:List<Persons>)
    :ArrayAdapter<Persons>( mCtx, resource, items) {
    //params = context, layout id, list of data

    //TODO create custom adapter, create class and extend the arrayadapter<Class> and
    //TODO override getview method - create new item View for each row of a listView, 4 objects = 4 view 4 rows adapter,
    //TODO this method getview is called when list items going to be displayed
    //convert view - for recycling view, screen display 5 item, once scroll down, https://stackoverflow.com/questions/10120119/how-does-the-getview-method-work-when-creating-your-own-custom-adapter
    // to item 6, item 1 out of screen, generate view for item 6 will call getView()
    // item 1 will be convertView, which can be reused to check if its null and create new view else reuse convertView but not needed
    //parentview = containing custom adapter for view that contain item view that getView() generates, e.g. listView in the xml

    //position = correspending line/index on the listView to this view, e.g. first position = 0 is first row
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //layout inflator get dynamic View object that is defined from my_list_item.xml
        //TODO maybe check if an existing view is being reused, otherwise inflate view? recycle? or use viewholder if too much data
        val layoutInflator: LayoutInflater = LayoutInflater.from(mCtx)

        val view:View = layoutInflator.inflate(resource,null) // .inflate create view object from xml resource
        val imageView: ImageView = view.findViewById(R.id.imageViewPicture)
        val textView: TextView = view.findViewById(R.id.imageText1)
        val textView1: TextView = view.findViewById(R.id.imageText2)
        val textView2: TextView = view.findViewById(R.id.imageText3)

        val person: Persons = items[position]

        imageView.setImageDrawable(mCtx.resources.getDrawable(person.photo))
        // FIXME try imageView.setImageResource(person.photo) later on, right now if comment out
        // FIXME the image view the images still display on first page, probably cos its not dynamic yet
        // TODO this function is to dynamic change the text,image from XML to the objects added
        textView.text = person.firstName + " "
        textView1.text = person.lastName + " "
        textView2.text = person.address

        Log.e("getView() is called", "person position --> \n " + person.firstName)
        //return super.getView(position, convertView, parent)
        return view
    }
}