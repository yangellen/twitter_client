package com.codepath.apps.restclienttemplate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.TwitterApplication
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.apps.restclienttemplate.network_client.TwitterClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button
    lateinit var client: TwitterClient
    lateinit var countDown: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)
        countDown = findViewById(R.id.countDown)

        client = TwitterApplication.getRestClient(this)

        etCompose.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Fires right as the text is being changed (even supplies the range of text)
                val totalCount = etCompose.length()
                val remainCount = 280 - totalCount
                countDown.setText(remainCount.toString())

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Fires right before text is changing
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        btnTweet.setOnClickListener{
            //grab the content of edit text for etComoose
            val tweetContent = etCompose.text.toString()

            //validate tweet
            if (tweetContent.isEmpty()){
                Toast.makeText(this,"Empty tweets not allowed!",Toast.LENGTH_SHORT).show()
            }else
            if (tweetContent.length > 280){
                Toast.makeText(this,"Tweet is too long! Limit is 140 characters!",Toast.LENGTH_SHORT).show()
            }else{
            //make api call to Twitter to public tweet
                client.publishTweet(tweetContent, object: JsonHttpResponseHandler(){
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                        //send tweet back to timeline activity
                        val tweet = Tweet.fromJson(json.jsonObject)

                        val intent = Intent()
                        intent.putExtra("tweet", tweet)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG, "Failed to publish tweet", throwable)
                    }



                })

            }
        }
    }
    companion object{
        val TAG = "ComposeActivity"
    }
}