package com.parandak.ensaf8.DirectionManagement;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlPullParserHandlerForWpt {

    private List<wpt> wpts= new ArrayList<wpt>();
    private wpt wpt;
    private String text;

    public List<wpt> getWpts() {
        return wpts;
    }

    public List<wpt> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("wpt")) {
                            // create a new instance of wpt
                            wpt = new wpt();
                            wpt.setLat(parser.getAttributeValue(null, "lat"));
                            wpt.setLon(parser.getAttributeValue(null, "lon"));
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("wpt")) {
                            // add wpt object to list
                            wpts.add(wpt);
                        } else if (tagname.equalsIgnoreCase("name")) {
                            wpt.setName(text);
                        }
                        else if (tagname.equalsIgnoreCase("time")) {
                            wpt.setDate(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return wpts;
    }
}
