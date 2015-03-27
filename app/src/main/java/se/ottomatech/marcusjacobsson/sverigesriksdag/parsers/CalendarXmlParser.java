package se.ottomatech.marcusjacobsson.sverigesriksdag.parsers;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.CalendarPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-21.
 */
public class CalendarXmlParser {

    // We don't use namespaces
    private static final String ns = null;

    public ArrayList<CalendarPojo> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readKalenderLista(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList<CalendarPojo> readKalenderLista(XmlPullParser parser) throws XmlPullParserException, IOException {

        ArrayList<CalendarPojo> entries = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "kalenderlista");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("kalender")) {
                entries.add(readKalender(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private CalendarPojo readKalender(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "kalender");

        String categories = null;
        String location = null;
        String uid = null;
        String transp = null;
        String dtStart = null;
        String dtEnd = null;
        String created = null;
        String lastModified = null;
        String summary = null;
        String description = null;
        String comment = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("CATEGORIES")) {
                categories = readCategories(parser);
            } else if (name.equals("LOCATION")) {
                location = readLocation(parser);
            } else if (name.equals("UID")) {
                uid = readUid(parser);
            } else if (name.equals("TRANSP")) {
                transp = readTransp(parser);
            } else if (name.equals("DTSTART")) {
                dtStart = readDtstart(parser);
            } else if (name.equals("DTEND")) {
                dtEnd = readDtend(parser);
            } else if (name.equals("CREATED")) {
                created = readCreated(parser);
            } else if (name.equals("LAST-MODIFIED")) {
                lastModified = readLastModified(parser);
            } else if (name.equals("SUMMARY")) {
                summary = readSummary(parser);
            } else if (name.equals("DESCRIPTION")) {
                description = readDescription(parser);
            } else if (name.equals("COMMENT")) {
                comment = readComment(parser);
            } else {
                skip(parser);
            }
        }
        return new CalendarPojo(categories, location, uid, transp, dtStart, dtEnd, created, lastModified,
                summary, description, comment);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private String readCategories(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "CATEGORIES");
        String categories = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "CATEGORIES");
        return categories;
    }

    private String readLocation(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "LOCATION");
        String location = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "LOCATION");
        return location;
    }

    private String readUid(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "UID");
        String uid = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "UID");
        return uid;
    }

    private String readTransp(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "TRANSP");
        String transp = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "TRANSP");
        return transp;
    }

    private String readDtstart(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "DTSTART");
        String dtStart = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "DTSTART");
        return dtStart;
    }

    private String readDtend(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "DTEND");
        String dtEnd = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "DTEND");
        return dtEnd;
    }

    private String readCreated(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "CREATED");
        String created = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "CREATED");
        return created;
    }

    private String readLastModified(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "LAST-MODIFIED");
        String lastModified = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "LAST-MODIFIED");
        return lastModified;
    }

    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "SUMMARY");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "SUMMARY");
        return summary;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "DESCRIPTION");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "DESCRIPTION");
        return description;
    }

    private String readComment(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "COMMENT");
        String comment = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "COMMENT");
        return comment;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
