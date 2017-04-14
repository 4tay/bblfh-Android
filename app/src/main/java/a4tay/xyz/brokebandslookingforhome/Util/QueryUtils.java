package a4tay.xyz.brokebandslookingforhome.Util;

import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    public static String getJSONFromUrl(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with input", e);
        }

        return jsonResponse;

    }


    private static URL createUrl(String strUrl) {
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Couldn't Create URL: ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If we connect and all things are good, we'll return 200

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error!! Response: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "probz with retrieving: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Couldn't close my inputStream: ", e);
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String singleLine = bufferedReader.readLine();
            while (singleLine != null) {
                stringBuilder.append(singleLine);
                singleLine = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    public static ArrayList<Events> makeEventFromJSON(String input) {

        ArrayList<Events> eventsList = new ArrayList<>();


        try {
            JSONObject fullOb = new JSONObject(input);
            JSONArray shows = fullOb.optJSONArray("shows");
            for(int i = 0; i < shows.length(); i++) {
                ArrayList<Event> eventList = new ArrayList<>();
                Events events = new Events();
                JSONObject homeShows = shows.optJSONObject(i);
                events.setLocationName(homeShows.optString("homeName"));
                Log.d(LOG_TAG, events.toString());
                JSONArray allEvents = homeShows.optJSONArray("tourDates");
                for(int j = 0; j < allEvents.length(); j++) {
                    JSONObject singleEvent = allEvents.optJSONObject(j);
                    int tdID = singleEvent.optInt("showID");
                    int tourID = singleEvent.optInt("tourID");
                    int tdType = singleEvent.optInt("showType");
                    String tdName = singleEvent.optString("showName");
                    String tdAddress = singleEvent.optString("showAddress");
                    float tdLat = singleEvent.optLong("showLat");
                    float tdLng = singleEvent.optLong("showLng");
                    int tdHomeConfirmed = singleEvent.getInt("homeConfirmed");
                    String tdDate = singleEvent.getString("showDate");
                    Event newEvent = new Event(tdName,tdDate);

                    eventList.add(newEvent);
                }
                events.setEvents(eventList);
                eventsList.add(events);
            }

            return eventsList;
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        return eventsList;
    }

    public static ArrayList<Events> makeBandListWithGenre(String input) {

        ArrayList<Events> eventsList = new ArrayList<>();

        try {
            JSONObject fullOb = new JSONObject(input);
            JSONArray genres = fullOb.optJSONArray("allGenresWithBands");
            for(int i = 0; i < genres.length(); i++) {
                Events events = new Events();
                ArrayList<Event> eventList = new ArrayList<>();
                JSONObject genre = genres.optJSONObject(i);
                events.setLocationName(genre.optString("genreName"));
                JSONArray bandsInGenre = genre.optJSONArray("bands");
                for(int j = 0; j < bandsInGenre.length(); j++) {
                    JSONObject singleBand = bandsInGenre.optJSONObject(j);
                    int bandID = singleBand.optInt("bandID");
                    String bandName = singleBand.optString("bandName");
                    String bandPhoto = singleBand.getString("bandPhoto");
                    Event newEvent = new Event(bandName,genre.getString("genreName"),bandPhoto);

                    eventList.add(newEvent);
                }
                events.setEvents(eventList);
                eventsList.add(events);
            }


            return eventsList;
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        return eventsList;
    }

    public static ArrayList<Home> getFanHomes(String input) {
        ArrayList<Home> homeList = new ArrayList<>();

        try {
            JSONObject fullOb = new JSONObject(input);
            JSONArray allHomes = fullOb.getJSONArray("homes");
            for(int i = 0; i < allHomes.length(); i++) {
                JSONObject home = allHomes.getJSONObject(i);
                int homeID = home.optInt("homeID");
                int fanID = home.optInt("fanID");
                String homeName = home.optString("homeName");
                String homeAddress = home.optString("homeAddress");
                String homeAddressTwo = home.optString("homeAddressTwo");
                int homeZip = home.optInt("homeZip");
                String homeCity = home.optString("homeCity");
                String homeState = home.optString("homeState");
                String homeCountry = home.optString("homeCountry");
                float homeLat = home.optLong("homeLat");
                float homeLng = home.optLong("homeLng");
                String homePhoto = home.optString("homePhoto");

                Home homeAdd = new Home(homeID,fanID,homeName,homeAddress,homeAddressTwo,
                        homeZip, homeCity, homeState, homeCountry, homeLat, homeLng, homePhoto);
                homeList.add(homeAdd);
            }
            return homeList;
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        return homeList;

    }
    public static ArrayList<Band> getBandList(String input) {
        ArrayList<Band> bandList = new ArrayList<>();

        try {
            JSONObject fullOb = new JSONObject(input);
            JSONArray allBands = fullOb.getJSONArray("bands");
            for(int i = 0; i < allBands.length(); i++) {
                JSONObject band = allBands.getJSONObject(i);
                String bandName = band.optString("bandName");
                String bandGenre = band.optString("bandGenre");
                int bandID = band.optInt("bandID");
                Band bandAdd = new Band(bandName,bandGenre,bandID);
                bandList.add(bandAdd);
            }
            return bandList;
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        return bandList;
    }
    public static String getLogin(String serverResponse) {
        try {
            JSONObject user = new JSONObject(serverResponse);

            return user.toString();
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Errorr....", e);
        }

        return"";
    }

    public static String newURL(JSONObject params, String stringURL) {
        try {

            URL url = new URL(stringURL); // here is your URL path
            Log.d("params", params.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(10000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";

                if ((line = in.readLine()) != null) {
                    stringBuffer.append(line);
                }

                in.close();
                return stringBuffer.toString();

            } else {
                Log.e(LOG_TAG,String.valueOf(responseCode));
                return "";
            }
        } catch (Exception e) {
            Log.e(LOG_TAG,"Exception",e);
            return "";
        }

    }
        private static String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    public static String getText(EditText getFromIt) {

        if(getFromIt != null) {


            return getFromIt.getText().toString().trim();

        }
        return "";
    }
}
