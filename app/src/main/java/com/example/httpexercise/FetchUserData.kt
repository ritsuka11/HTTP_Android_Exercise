package com.example.httpexercise

import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FetchUserData(private val listener: OnDataFetchedListener) :
    AsyncTask<String, Void, List<User>>() {

    interface OnDataFetchedListener {
        fun onDataFetched(users: List<User>)
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): List<User> {
        val url = URL(params[0])
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        try {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            return parseJson(response.toString())
        } finally {
            connection.disconnect()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: List<User>?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.onDataFetched(result)
        }
    }

    private fun parseJson(json: String): List<User> {
        val userList = ArrayList<User>()

        try {
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val userJson: JSONObject = jsonArray.getJSONObject(i)
                val user = User(
                    userJson.getInt("id"),
                    userJson.getString("name"),
                    userJson.getString("username"),
                    userJson.getString("email"),
                    parseAvatar(userJson.getJSONObject("avatar")),
                    parseAddress(userJson.getJSONObject("address")),
                    userJson.getString("phone"),
                    userJson.getString("website"),
                    parseCompany(userJson.getJSONObject("company"))
                )
                userList.add(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return userList
    }

    private fun parseAvatar(avatarJson: JSONObject): Avatar {
        return Avatar(
            avatarJson.getString("thumbnail"),
            avatarJson.getString("photo")
        )
    }

    private fun parseAddress(addressJson: JSONObject): Address {
        return Address(
            addressJson.getString("street"),
            addressJson.getString("suite"),
            addressJson.getString("city"),
            addressJson.getString("zipcode"),
            parseGeo(addressJson.getJSONObject("geo"))
        )
    }

    private fun parseGeo(geoJson: JSONObject): Geo {
        return Geo(
            geoJson.getString("lat"),
            geoJson.getString("lng")
        )
    }

    private fun parseCompany(companyJson: JSONObject): Company {
        return Company(
            companyJson.getString("name"),
            companyJson.getString("catchPhrase"),
            companyJson.getString("bs")
        )
    }
}
