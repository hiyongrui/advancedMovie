package com.example.tanyongrui.advancedmovie

import com.example.tanyongrui.advancedmovie.R.id.*
import java.io.Serializable

// implement Serializable so that object can be passed and called in the intent
class MovieEntity(var title:String = "",var overview:String = "",var language:String = "",
                  var releaseDate:String = "", var suitableAge:String = "", var photo:Int = 0,
                  var reviewNoOfStars:String = "", var reviewDescription:String = "") : Serializable{
    /* var title: String = "Venom"
    var overview: String = "When Eddie Brock acquires the powers of a symbiote, he will have to release his alter-ego Venom to save his life."
    var language: String = "English"
    var releaseDate: String = "03-10-2018"
    var suitableAge: Boolean = true
    */
    /*
    var title: String = ""
    var overview: String = ""
    var language: String = ""
    var releaseDate: String = ""
    var suitableAge: String = ""
    var reviewNoOfStars: String = ""
    var reviewDescription: String = ""
    */

    //override ToString method to print out the object, if not it will be like
    // I/System.out: com.example.tanyongrui.intermediatemovie.MovieEntity@a449cf6
    override fun toString(): String {
        return "title = " + title +
                "\n overview = " + overview +
                "\n language = " + language +
                "\n release date = " + releaseDate +
                "\n suitable for children = " + suitableAge +
                "\n reviews no of stars = " + reviewNoOfStars +
                "\n reviews description = " + reviewDescription
    }
}
