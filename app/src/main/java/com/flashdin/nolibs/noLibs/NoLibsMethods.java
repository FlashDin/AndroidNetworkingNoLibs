package com.flashdin.nolibs.noLibs;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by flashdin on 10/11/17.
 */

public class NoLibsMethods {

    private DataModel domains;
    private List<DataModel> mDataList;

    public List<DataModel> viewData(Context ctx) {
        mDataList = new ArrayList<DataModel>();
        String filter = "";
        String mDomain = "sUser/view.php";
        try {
            String mData = URLEncoder.encode("filter", "UTF-8")+"="+URLEncoder.encode(filter, "UTF-8");
            AsyncTaskGetMethod asy = new AsyncTaskGetMethod(ctx);
            asy.execute(mDomain, mData);
            String hsl = asy.get();
            if (hsl != null) {
                JSONArray jArray = new JSONArray(hsl.replace("\\\"", "\"").replace("\n", "").replaceAll("^\"|\"$", "").trim());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    domains = new DataModel();
                    domains.setmId(json_data.getString("id"));
                    domains.setmUsername(json_data.getString("username"));
                    domains.setmFoto(json_data.getString("foto"));
                    mDataList.add(domains);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mDataList;
    }

    public void saveData(DataModel dm, Context ctx) {
        try {
            String mDomain = "";
            String mData = "";
            if (dm.getmId().equals("")) {
                mDomain = "sUser/add.php";
                mData = URLEncoder.encode("a", "UTF-8")+"="+URLEncoder.encode(dm.getmUsername(), "UTF-8")
                        +"&"+URLEncoder.encode("b", "UTF-8")+"="+URLEncoder.encode(dm.getmFoto(), "UTF-8");
            } else {
                mDomain = "sUser/update.php";
                mData = URLEncoder.encode("a", "UTF-8")+"="+URLEncoder.encode(dm.getmId(), "UTF-8")
                        +"&"+URLEncoder.encode("b", "UTF-8")+"="+URLEncoder.encode(dm.getmUsername(), "UTF-8")
                        +"&"+URLEncoder.encode("c", "UTF-8")+"="+URLEncoder.encode(dm.getmFoto(), "UTF-8");
            }
            AsyncTaskMethod asy = new AsyncTaskMethod(ctx);
            asy.execute(mDomain, mData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(DataModel dm, Context ctx) {
        try {
            String mDomain = "sUser/delete.php";
            String mData = URLEncoder.encode("a", "UTF-8")+"="+URLEncoder.encode(dm.getmId(), "UTF-8");
            AsyncTaskMethod asy = new AsyncTaskMethod(ctx);
            asy.execute(mDomain, mData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
