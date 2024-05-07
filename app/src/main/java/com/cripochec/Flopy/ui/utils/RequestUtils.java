package com.cripochec.Flopy.ui.utils;

import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.westernyey.Flopy.ui.login.FragmentLogin;
import com.westernyey.Flopy.ui.register.FragmentRegister;
import com.westernyey.Flopy.ui.register.FragmentRegisterCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtils extends AsyncTask<String, Void, String> {

    private final WeakReference<Fragment> fragmentRef;
    private final String requestLine;
    private final String method;
    private final String data;

    public RequestUtils(Fragment fragment, String requestLine, String method, String data) {
        this.fragmentRef = new WeakReference<>(fragment);
        this.requestLine = requestLine;
        this.method = method;
        this.data = data;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection con = null;
        BufferedReader in = null;
        try {
            String URL_SERVER = "http://90.156.231.211:5000/" + requestLine;
//            String URL_SERVER = "http://192.168.0.102:5000/" + requestLine;
            URL url = new URL(URL_SERVER);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();

            os.write(data.getBytes());
            os.flush();
            os.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();

        } catch (IOException e) {
            Log.e("RequestUtils", "Error", e);
            return "ERROR " + e.getMessage();
        } finally {
            if (con != null) {
                con.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Log.e("RequestUtils", "Error closing stream", e);
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Fragment fragment = fragmentRef.get();
        if (fragment != null) {
            if (fragment instanceof FragmentRegister) {
                ((FragmentRegister) fragment).updateData(result);
            } else if (fragment instanceof FragmentRegisterCode) {
                ((FragmentRegisterCode) fragment).updateData(result);
            } else if (fragment instanceof FragmentLogin) {
                ((FragmentLogin) fragment).updateData(result);
            }
        }
    }
}
