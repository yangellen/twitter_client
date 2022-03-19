package com.codepath.apps.restclienttemplate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.R

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)

        btnTweet.setOnClickListener{
            //grab the content of edit text for etComoose
            val tweetContent = etCompose.text.toString()

            //validate tweet
            if (tweetContent.isEmpty()){
                Toast.makeText(this,"Empty tweets not allowed!",Toast.LENGTH_SHORT).show()
            }else
            if (tweetContent.length > 140){
                Toast.makeText(this,"Tweet is too long! Limit is 140 characters!",Toast.LENGTH_SHORT).show()
            }else{
            //make api call to Twitter to public tweet
            Toast.makeText(this, tweetContent, Toast.LENGTH_SHORT).show()
            }
        }
    }
}