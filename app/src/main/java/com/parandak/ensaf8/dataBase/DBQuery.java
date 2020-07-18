package com.parandak.ensaf8.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;


import com.parandak.ensaf8.app.App;

import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;

import org.osmdroid.util.GeoPoint;

import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;
import java.util.List;




public class DBQuery {
    ArrayList<Integer> colorsList=new ArrayList<>();
    Drawable newMarker,blue_marker,green_marker,orange_marker,red_marker,yellow_marker,cons_image;
    public DBQuery(){
        int color1,color2,color3,color4;
        color1 = Color.argb(75,255,255,0);
        color2 = Color.argb(75,255,75,0);
        color3 = Color.argb(75,75,75,0);
        color4 = Color.argb(75,255,75,0);
        colorsList.add(color1);
        colorsList.add(color2);
        colorsList.add(color3);
        colorsList.add(color4);
        Context context = App.getContext();

    }
    public Cursor getConstruction (String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " Constructions.lat,"
                + " Constructions.lon"
                + " FROM"
                + " Constructions"
                + " WHERE"
                + " Constructions.rowid =" + ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public String getCustomerName(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " Customers.CustomerName"
                + " FROM"
                + " Customers"
                + " WHERE"
                + " Customers.rowid =" + ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            return cursor.getString(0);
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return "query not worked !";

    }
    public Cursor getCustomerPhoneNumber(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " PhoneNumber.Number"
                + " FROM"
                + " PhoneNumber"
                + " WHERE"
                + " PhoneNumber.customerID =" + ID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;

    }
    public Cursor showAllConstructions(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String viewQuery = "CREATE VIEW IF NOT EXISTS lastUpdateCons"
                +" AS SELECT constructionID,phase as lastPhase,MAX(phaseDate) as lastPhaseDate"
                +" FROM"
                +" ConstructionPhase"
                +" GROUP BY constructionID;";
        db.execSQL(viewQuery);
        String selectQuery = "SELECT"
                +" Constructions.Cons_ID,"
                +" Constructions.constructionName,"
                +" Constructions.lat,"
                +" Constructions.lon,"
                +" lastUpdateCons.lastPhase,"
                +" lastUpdateCons.lastPhaseDate"
                +" FROM Constructions"
                +" INNER JOIN lastUpdateCons"
                +" ON Constructions.rowid = lastUpdateCons.constructionID"
                +" ORDER BY Constructions.lat,Constructions.lon ASC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor showTest(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        /*String selectQuery = "SELECT"
                +" indID,"
                +" phase,"
                +" MAX(phaseDate)"
                +" FROM Cons_Phase"
                +" GROUP BY indID;";*/
        String selectQuery = "SELECT"
                +" Individual.ID_Indi,"
                +" Individual.indiName,"
                +" GPoint.lat,"
                +" GPoint.lon"
                +" FROM Individual"
                +" INNER JOIN Indi_Geop"
                +" ON Individual.ID_Indi = Indi_Geop.indiID"
                +" INNER JOIN GPoint"
                +" ON Indi_Geop.geopID = GPoint.IDGeop;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }
    public Cursor getGeoPoint(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * "
                +" FROM " + GPoint.TABLE + " ;" ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor showSelectedConstruction(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String viewQuery = "CREATE VIEW IF NOT EXISTS lastUpdateCons"
                +" AS SELECT constructionID,phase as lastPhase,MAX(phaseDate) as lastPhaseDate"
                +" FROM"
                +" ConstructionPhase"
                +" GROUP BY constructionID;";
        db.execSQL(viewQuery);
        String selectQuery = "SELECT"
                +" Constructions.Cons_ID,"
                +" Constructions.constructionName,"
                +" Constructions.lat,"
                +" Constructions.lon,"
                +" lastUpdateCons.lastPhase,"
                +" lastUpdateCons.lastPhaseDate"
                +" FROM Constructions"
                +" INNER JOIN lastUpdateCons"
                +" ON Constructions.Cons_ID = lastUpdateCons.constructionID"
                +" WHERE Constructions.Cons_ID = " + consID + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor singleTapOnCon(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" Constructions.constructionName,"
                +" Customers.CustomerName,"
                +" ConstructionCustomer.position,"
                +" Constructions.lat,"
                +" Constructions.lon"
                +" FROM Constructions"
                +" INNER JOIN ConstructionCustomer"
                +" ON ConstructionCustomer.constructionID ="
                +" Constructions.rowid"
                +" INNER JOIN Customers"
                +" ON Customers.rowid ="
                +" ConstructionCustomer.customerID"
                +" WHERE Constructions.rowid = "+ ID
                +" GROUP BY constructionID;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor singleTapOnSecond(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " Customers.rowid,"
                + " Customers.CustomerName,"
                + " ConstructionCustomer.position"
                + " FROM"
                + " ConstructionCustomer"
                + " INNER JOIN Customers"
                + " ON Customers.rowid = ConstructionCustomer.customerID"
                + " WHERE ConstructionCustomer.constructionID = "+ ID ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getAllCustomer(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " Customers.rowid,"
                + " Customers.CustomerName"
                + " FROM"
                + " Customers;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getAllCustomerSearchFilter(String filter){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " Customers.rowid,"
                + " Customers.CustomerName"
                + " FROM"
                + " Customers"
                + " WHERE "
                + " Customers.CustomerName LIKE '%"+filter+"%';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor singleTapOnSecondPhone(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " PhoneNumber.Number"
                + " FROM"
                + " PhoneNumber"
                + " WHERE PhoneNumber.customerID = "+ ID ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor constructionPosition (String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                + " Constructions.rowid,"
                + " Constructions.constructionName,"
                + " ConstructionCustomer.position"
                + " FROM"
                + " ConstructionCustomer"
                + " INNER JOIN Constructions"
                + " ON Constructions.rowid = ConstructionCustomer.constructionID"
                + " WHERE ConstructionCustomer.customerID = "+ ID ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getFollow(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "
                + " Follow.rowid,"
                + " Follow.detail,"
                + " Follow.followDate"
                + " FROM"
                + " Follow"
                + " WHERE Follow.customerID = "+ ID ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public int getRegionID(String RegionName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "select"
                +" rowid"
                +" from"
                +" Region"
                +" where"
                +" regionName = '"
                +RegionName
                +"';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i,C;
        i = cursor.getColumnIndex("rowid");
        if(cursor.moveToFirst()){
            C = cursor.getInt(i);
        }else {
            C=-1;
        }
        DatabaseManager.getInstance().closeDatabase();
        cursor.close();
        return C;

    }
    public Cursor getPointOfRegion(String regionName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" lat,"
                +" lon"
                +" FROM"
                +" Points"
                +" WHERE"
                +" regionID = ("
                +" SELECT"
                +" Region.rowid"
                +" FROM"
                +" Region"
                +" WHERE"
                +" Region.regionName = '"
                +"23"
                +"' )";
        Cursor cursor = db.rawQuery(selectQuery, null);
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor getPointOfRegionID(String regionName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" lat,"
                +" lon"
                +" FROM"
                +" Points"
                +" WHERE"
                +" regionID = " + regionName;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor getPoint(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" lat,"
                +" lon"
                +" FROM "
                +" Points;";
        Cursor cursor = db.rawQuery(selectQuery,null);
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public List<Polygon> getPolygonList (){
        String regionID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQueryRegionID = "SELECT"
                +" Region.rowid"
                +" FROM"
                +" Region;";

        Cursor cursorRegionID = db.rawQuery(selectQueryRegionID, null);
        List<Polygon> polygonList = new ArrayList<>();
        int ccc = 0;
        while(cursorRegionID.moveToNext())
        {

            regionID = cursorRegionID.getString(0);
            Polygon polygon = new Polygon();
            List<GeoPoint> geoPointList = new ArrayList<>();
            String selectQuery = "SELECT"
                    +" lat,"
                    +" lon"
                    +" FROM"
                    +" Points"
                    +" WHERE"
                    +" regionID = " + regionID;
            Cursor cursorPointsOfRegion = db.rawQuery(selectQuery, null);
            while (cursorPointsOfRegion.moveToNext())
            {
                GeoPoint point;
                float lat = cursorPointsOfRegion.getFloat(0);
                float lon = cursorPointsOfRegion.getFloat(1);
                point = new GeoPoint(lat,lon);
                geoPointList.add(point);
            }
            if (geoPointList.size()!=0) {
                geoPointList.add(geoPointList.get(0));
            }

            polygon.setFillColor(colorsList.get(ccc));
            polygon.setPoints(geoPointList);
            polygon.setStrokeWidth(1);
            polygon.setTitle("Test");
            polygon.setId(regionID);
            polygonList.add(polygon);
            ccc++;

        }
        return polygonList;

    }
    public int getPolytest (){
        String regionID;
        int ii=0;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQueryRegionID = "SELECT"
                +" Region.rowid"
                +" FROM"
                +" Region;";

        Cursor cursorRegionID = db.rawQuery(selectQueryRegionID, null);
        List<Polygon> polygonList = new ArrayList<>();
        while(cursorRegionID.moveToNext())
        {
            ii++;
            regionID = cursorRegionID.getString(0);
            Polygon polygon = new Polygon();
            List<GeoPoint> geoPointList = new ArrayList<>();
            String selectQuery = "SELECT"
                    +" lat,"
                    +" lon"
                    +" FROM"
                    +" Points"
                    +" WHERE"
                    +" regionID = " + regionID;
            Cursor cursorPointsOfRegion = db.rawQuery(selectQuery, null);
            while (cursorPointsOfRegion.moveToNext())
            {
                GeoPoint point;
                float lat = cursorPointsOfRegion.getFloat(0);
                float lon = cursorPointsOfRegion.getFloat(1);
                point = new GeoPoint(lat,lon);
                geoPointList.add(point);
            }
            if (geoPointList.size()!=0) {
                geoPointList.add(geoPointList.get(0));
            }
            int color1,color2,color3,color4;
            color1 = Color.argb(255,255,255,0);
            color2 = Color.argb(255,255,75,0);
            color3 = Color.argb(255,75,75,0);
            color4 = Color.argb(75,255,75,0);
            polygon.setFillColor(color2);
            polygon.setPoints(geoPointList);
            polygon.setStrokeWidth(1);
            polygon.setTitle("Test");
            polygon.setId(regionID);
            polygonList.add(polygon);

        }
        return ii;
    }
    public Cursor getMaxPhase(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" MAX(phaseDate)"
                +" FROM "
                +" ConstructionPhase"
                +" WHERE ConstructionPhase.constructionID =" + ID;
        String selectQuery1 = "SELECT"
                +" phaseDate"
                +" FROM "
                +" ConstructionPhase"
                +" WHERE ConstructionPhase.constructionID =" + ID;

        Cursor cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor getPhase(String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" phaseDate"
                +" FROM "
                +" ConstructionPhase"
                +" WHERE ConstructionPhase.constructionID =" + ID;
        Cursor cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor getAllConstructionPhase(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" ConstructionPhase.constructionID,"
                +" ConstructionPhase.phase,"
                +" ConstructionPhase.phaseDate"
                +" FROM "
                +" ConstructionPhase"
                +" ORDER BY"
                +" ConstructionPhase.rowid DESC;";
        Cursor cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }
    public Cursor getLastUpdateCons (String ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT"
                +" constructionID,"
                +" phase as lastPhase,"
                +" MAX(phaseDate) as lastPhaseDate"
                +" FROM "
                +" ConstructionPhase"
                +" where ConstructionPhase.constructionID =" + ID ;
        Cursor cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }

    }
