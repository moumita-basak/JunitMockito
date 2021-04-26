package com.infosys.junitmockito

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object AppManager {
    const val SHARED_PREF_CURRENCY_IQD = "Currency_IQD"

    @Throws(JSONException::class)
    fun nullChecked(`object`: JSONObject, key: String?): String {
        return if (!`object`.has(key) || `object`.isNull(key)) {
            ""
        } else `object`.getString(key!!)
    }

    @Throws(JSONException::class)
    fun nullCheckedArray(`object`: JSONObject?, key: String?): JSONArray {
        return if (`object` == null || `object`.isNull(key) || !`object`.has(key) || `object`.getString(key!!
            ) == "0"
        ) {
            JSONArray("[]")
        } else `object`.getJSONArray(key)
    }

    @Throws(JSONException::class)
    fun nullCheckedObj(
        `object`: JSONObject,
        key: String?
    ): JSONObject? {
        return if (!`object`.has(key) || `object`.isNull(key)) {
            JSONObject("{}")
        } else `object`.getJSONObject(key!!)
    }

    @Throws(JSONException::class)
    fun nullCheckedDefaultZero(
        `object`: JSONObject,
        key: String?
    ): String? {
        return if (!`object`.has(key) || `object`.isNull(key) || `object`.getString(key!!)
                .equals("", ignoreCase = true)
        ) {
            "0"
        } else `object`.getString(key)
    }
}