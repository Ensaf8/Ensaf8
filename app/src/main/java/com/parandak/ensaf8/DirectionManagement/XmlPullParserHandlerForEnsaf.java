package com.parandak.ensaf8.DirectionManagement;

import android.util.Log;

import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.PhoneNum;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Cons_PhaseRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.CusAccountRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.GPointRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_CoopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_GeopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.PhoneNumRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.TendRepo;
import com.parandak.ensaf8.homePage.HomePageActivity;
import com.parandak.ensaf8.storage.EnsafQueryExport;
import com.parandak.ensaf8.storage.ExImportContract;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlPullParserHandlerForEnsaf {
    private final String TAG = this.getClass().getSimpleName();

    private Cons_Phase cons_phase;
    private CusAccount cusAccount;
    private GPoint gPoint;
    private Indi_Coop indi_coop;
    private Indi_Geop indi_geop;
    private Individual individual;
    String indiFId = null;
    String indiIdFromIndiF = null;
    private List<Individual.syncLink> indSyncLinkList = new ArrayList<>();

    boolean isMyFile;
    boolean isMyData;
    boolean isMyIndi;
    //boolean isF_before;

    private Individual.syncLink indSyncLinkF;
    private PhoneNum phoneNum;
    private Tend tend;
    private String text;

    private String cusIdIndi;
    private String cusID;

    private List<wpt> wpts= new ArrayList<wpt>();
    private wpt wpt;

    public List<wpt> getWpts() {
        return wpts;
    }

    public void importFile (InputStream is){
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        //////
                        if(HomePageActivity.isConnected) {
                            startTagSyncFile(tagname);
                        }else {
                            startTagSyncFile_AUTH(tagname);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        /////
                        initiateImport(tagname);///init cusID
                        isMyFile = cusID.equals(HomePageActivity.ID_CONNECT_Customer);
                        //isMyData = false;
                        if(HomePageActivity.isConnected) {
                            endTagSyncFile(tagname);
                        }else {
                            endTagSyncFile_AUTH(tagname);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        }catch (XmlPullParserException | IOException e) {e.printStackTrace();}
    }

    private void initiateImport(String tagname){
        if (tagname.equalsIgnoreCase(EnsafQueryExport.customerID)) {
            // initiate
            cusID = text;
            Log.d("ensaf::::::::", TAG + " >CustomerID : " + EnsafQueryExport.customerID +  " : " +
                    text);
        }
    }

    private void startTagSyncFile(String tagname){
        if (tagname.equalsIgnoreCase(Individual.TABLE)){
            cusIdIndi = null;
            //isF_before = false;
            indSyncLinkList.clear();
            isMyData =false;
            indiIdFromIndiF = null;
            this.individual = new Individual();
            this.indSyncLinkF = new Individual.syncLink();
            Log.d("ensaf::::::::", TAG + "> initiate : " + Individual.TABLE);
        }else if (tagname.equalsIgnoreCase(Cons_Phase.TABLE)) {
            // create a new instance of ConstructionCustomer
            cons_phase = new Cons_Phase();
            Log.d("ensaf::::::::", TAG + "> initiate : " + Cons_Phase.TABLE);
        }else if (tagname.equalsIgnoreCase(GPoint.TABLE)){
            // create a new instance of Constructions
            isMyIndi = false;
            gPoint = new GPoint();
            Log.d("ensaf::::::::", TAG + "> initiate : " + GPoint.TABLE);
            indi_geop = new Indi_Geop();
            Log.d("ensaf::::::::", TAG + "> initiate : " + Indi_Geop.TABLE);
        }
    }

    private void startTagSyncFile_AUTH(String tagname){
        if (tagname.equalsIgnoreCase(CusAccount.TABLE)){
            // create a new instance of ConstructionPhase
            cusAccount = new CusAccount();
            Log.d("ensaf::::::::", TAG + " startTagSyncFile_AUTH> initiate : " + CusAccount.TABLE);
        }else if (tagname.equalsIgnoreCase(Individual.TABLE + ExImportContract.auth)){
            // create a new instance of PhoneNumber
            cusIdIndi = null;
            indSyncLinkList.clear();
            this.individual = new Individual();
            this.indSyncLinkF = new Individual.syncLink();
            Log.d("ensaf::::::::", TAG + " startTagSyncFile_AUTH> initiate : " + Individual.TABLE);
        }
    }

    private void endTagSyncFile(String tagname){
        IndividualRepo individualRepo = new IndividualRepo();
        IndividualRepo.syncLink syncFLinkRepo = new IndividualRepo.syncLink(true);
        IndividualRepo.syncLink syncTLinkRepo = new IndividualRepo.syncLink(false);
        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
        GPointRepo gPointRepo = new GPointRepo();
        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
        EnsafQueryExport ensafQueryExport = new EnsafQueryExport();
        int insertedIndiId;
        int insertedGpointId;
        if (tagname.equalsIgnoreCase(Individual.TABLE)) {
            indiFId = null;
            if (isMyFile){
                Log.d("ensaf::::::::", TAG + " endTagSyncFile : this is my data ");
                //individual.setID_Indi(indSyncLinkFlist.get(myDataIndex).getIndiFID());
                //indSyncLinkFlist.remove(myDataIndex);
            }else {
                //###########test
                if (!isMyData){
                    if (indSyncLinkList.size()>0){
                        insertedIndiId = individualRepo.insert(individual);
                        if (insertedIndiId > 0){
                            Log.d("ensaf::::::::", TAG + " endTagSyncFile : " + insertedIndiId + " > is inserted  : " + Individual.TABLE);
                            String insertedIndividualId = String.valueOf(insertedIndiId);
                            Log.d("ensaf::::::::", TAG + " endTagSyncFile> setIndiID " + insertedIndividualId + " to : " + "indSyncLinkF");
                            for (int i = 0; i< indSyncLinkList.size() ; i++){
                                indSyncLinkList.get(i).setIndiID(insertedIndividualId);
                                if (syncFLinkRepo.insert(indSyncLinkList.get(i))>0){
                                    Log.d("ensaf::::::::", TAG + " endTagSyncFile YAHOO!> inserted data to  : " + Individual.syncLink.TABLE_T +
                                            " id : " + indSyncLinkList.get(i).getIndiID());
                                }
                            }
                        }else{
                            Log.d("ensaf::::::::", TAG + " endTagSyncFile> NOT inserted data to  : " + Individual.TABLE);
                        }
                    }
                }else {
                    for (int i = 0; i< indSyncLinkList.size() ; i++){
                        indSyncLinkList.get(i).setIndiID(indiIdFromIndiF);
                        if (syncTLinkRepo.insert(indSyncLinkList.get(i))>0){
                            Log.d("ensaf::::::::", TAG + " endTagSyncFile YAHOO!> inserted data to  : " + Individual.syncLink.TABLE_T +
                                    " id : " + indSyncLinkList.get(i).getIndiID());
                        }
                    }
                }
            }
        }else if (tagname.equalsIgnoreCase(Individual.KEY_ID_Indi)) {
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Individual.KEY_ID_Indi);
            //
            if(isMyFile){
                individual.setID_Indi(text);
            }else {
                indSyncLinkF.setIndiFID(text);
                indSyncLinkF.setCusID(cusID);
                indSyncLinkList.add(indSyncLinkF);
            }
        }else if (tagname.equalsIgnoreCase(Individual.KEY_IndiName)) {
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Individual.KEY_IndiName);
            individual.setIndiName(text);
        }else if (tagname.equalsIgnoreCase(ExImportContract.indiFIdIndi)) {
            ////
            indiFId = text;
        }else if (tagname.equalsIgnoreCase(ExImportContract.cusIdIndi)) {

            if (text.equals(HomePageActivity.ID_CONNECT_Customer)){
                isMyData = true;
                indiIdFromIndiF = indiFId;
            }else {
                if (ensafQueryExport.indiFromIndiF(indiFId,text)!=null){
                    ///Must insert Individual;
                    isMyData = true;
                    indiIdFromIndiF = ensafQueryExport.indiFromIndiF(indiFId,text);
                    if (ensafQueryExport.indiFromIndiT(indiFId,text)==null){
                        indSyncLinkF = new Individual.syncLink();
                        Log.d("ensaf::::::::", TAG + " endTagSyncFile> init indSync for : syncIndiF");
                        indSyncLinkF.setIndiID(ensafQueryExport.indiFromIndiF(indiFId,indSyncLinkF.getCusID()));
                        Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + ensafQueryExport.indiFromIndiF(indSyncLinkF.getIndiFID(),indSyncLinkF.getCusID())
                                + " to : " + Individual.syncLink.KEY_IndiID);
                        indSyncLinkF.setIndiFID(indiFId);
                        Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + indiFId + " to : " + ExImportContract.indiFIdIndi);
                        indSyncLinkF.setCusID(text);
                        Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + ExImportContract.cusIdIndi);
                        indSyncLinkList.add(indSyncLinkF);
                        Log.d("ensaf::::::::", TAG + " endTagSyncFile> add indSync to  : indSyncLinkFlist");
                    }
                }else{
                    indSyncLinkF = new Individual.syncLink();
                    Log.d("ensaf::::::::", TAG + " endTagSyncFile> init indSync for : syncIndiF");
                    indSyncLinkF.setIndiFID(indiFId);
                    Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + indiFId + " to : " + ExImportContract.indiFIdIndi);
                    indSyncLinkF.setCusID(text);
                    Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + ExImportContract.cusIdIndi);
                    indSyncLinkList.add(indSyncLinkF);
                    Log.d("ensaf::::::::", TAG + " endTagSyncFile> add indSync to  : indSyncLinkFlist");
                }
            }
        }else if (tagname.equalsIgnoreCase(Individual.KEY_IsCons)) {
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Individual.KEY_IsCons);
            individual.setIsCons(text);
        }else if (tagname.equalsIgnoreCase(Cons_Phase.TABLE)&&cons_phase!=null) {
            // insert cons_phase
            if (cons_phaseRepo.insert(cons_phase)>0){
                Log.d("ensaf::::::::", TAG + " endTagSyncFile> insert data to  : " + Cons_Phase.TABLE);
            }else {
                Log.d("ensaf::::::::", TAG + " endTagSyncFile> NOT insert data to  : " + Cons_Phase.TABLE);
            }

        } else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_ID_Cons_Phase)&&cons_phase!=null) {
            if(isMyFile){
                cons_phase.setID_Cons_Phase(text);
                Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Cons_Phase.KEY_ID_Cons_Phase);
            }
        } else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_IndID)&&cons_phase!=null) {
            if (isMyFile){
                cons_phase.setIndID(text);
                Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " directly to : " + Cons_Phase.KEY_IndID);
            }else {
                //ensafQueryExport.indiFromIndiT(text ,cusID)
                cons_phase.setIndID(ensafQueryExport.indiFromIndiF(text ,cusID));
                Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + ensafQueryExport.indiFromIndiF(text, cusID) + " to : " + Cons_Phase.KEY_IndID);
            }
        }else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_Phase)&&cons_phase!=null) {
            cons_phase.setPhase(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Cons_Phase.KEY_Phase);
        }else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_PhaseDate)&&cons_phase!=null) {
            cons_phase.setPhaseDate(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Cons_Phase.KEY_PhaseDate);
            //#############
        }else if (tagname.equalsIgnoreCase(GPoint.TABLE)) {
            // insert gPoint
            if (!isMyIndi){
                insertedGpointId = gPointRepo.insert(gPoint);
                if (insertedGpointId>0){
                    Log.d("ensaf::::::::", TAG + " endTagSyncFile> insert data to  : " + GPoint.TABLE);
                    if (indi_geop!=null){
                        indi_geop.setGeopID(String.valueOf(insertedGpointId));
                        indi_geopRepo.insert(indi_geop);
                        Log.d("ensaf::::::::", TAG + " endTagSyncFile> insert data to  : " + Indi_Geop.TABLE);
                    }
                }
            }
        }else if (tagname.equalsIgnoreCase(GPoint.KEY_IDGeop)) {
            //gPoint.setIDGeop(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + GPoint.KEY_IDGeop);
            if (indi_geop!=null){
                indi_geop.setGeopID(text);
                Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Indi_Geop.KEY_GeopID);
            }
        }else if (tagname.equalsIgnoreCase(Indi_Geop.KEY_IndiID)) {
            if (isMyFile){
                indi_geop.setIndiID(text);
            }else {
                if (ensafQueryExport.indiFromIndiT(text ,cusID)!=null){
                    isMyIndi = true;
                }else {
                    indi_geop.setIndiID(ensafQueryExport.indiFromIndiF(text, cusID));
                    Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + Indi_Geop.KEY_IndiID);
                }
            }
        }else if (tagname.equalsIgnoreCase(GPoint.KEY_Lat)) {
            gPoint.setLat(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + GPoint.KEY_Lat);
        }else if (tagname.equalsIgnoreCase(GPoint.KEY_Lon)) {
            gPoint.setLon(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + GPoint.KEY_Lon);
        }else if (tagname.equalsIgnoreCase(GPoint.KEY_IsSolo)) {
            gPoint.setIsSolo(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile> add " + text + " to : " + GPoint.KEY_IsSolo);
        }
    }

    private void endTagSyncFile_AUTH(String tagname){
        IndividualRepo individualRepo = new IndividualRepo();
        CusAccountRepo cusAccountRepo = new CusAccountRepo();
        IndividualRepo.syncLink syncFLinkRepo = new IndividualRepo.syncLink(true);
        int insertedIndiId = 0;
        if (tagname.equalsIgnoreCase(CusAccount.TABLE)) {
            // insert cusAccount
            if (cusAccountRepo.insert(cusAccount)>0){
                Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> insert data to  : " + CusAccount.TABLE);
            }else {
                Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> NOT insert data to  : " + CusAccount.TABLE);
            }

        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_ID_Cus)) {
            cusAccount.setID_Cus(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + CusAccount.KEY_ID_Cus);
        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_IndID)) {
            cusAccount.setIndID(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + CusAccount.KEY_IndID);
        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_AccountName)) {
            cusAccount.setAccountName(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + CusAccount.KEY_AccountName);
        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_PassWord)) {
            cusAccount.setPassWord(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + CusAccount.KEY_PassWord);
        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_IsAct)) {
            cusAccount.setIsAct(text);
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + CusAccount.KEY_IsAct);
            //#############
        }else if (tagname.equalsIgnoreCase(Individual.TABLE + ExImportContract.auth)) {
            insertedIndiId = individualRepo.insert(individual);
            if (insertedIndiId > 0){
                Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> " + insertedIndiId + " is inserted  : " + Individual.TABLE + ExImportContract.auth);
                String insertedIndividualId = String.valueOf(insertedIndiId);
                Log.d("ensaf::::::::", TAG + "> setIndiID " + insertedIndividualId + " to : " + "indSyncLinkF");
                indSyncLinkF.setIndiID(insertedIndividualId);

                for (int i = 0; i< indSyncLinkList.size() ; i++){
                    indSyncLinkList.get(i).setIndiID(insertedIndividualId);
                    if (syncFLinkRepo.insert(indSyncLinkList.get(i))>0){
                        Log.d("ensaf::::::::", TAG + "> inserted data to  : " + Individual.syncLink.TABLE_F +
                                " id : " + indSyncLinkList.get(i).getIndiID());
                    }
                }
            }else{
                Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> NOT inserted data to  : " + Individual.TABLE);
            }
        }else if (tagname.equalsIgnoreCase(Individual.KEY_ID_Indi)) {
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + Individual.KEY_ID_Indi);

            if(isMyFile){
                individual.setID_Indi(text);
            }else {
                indSyncLinkF.setIndiFID(text);
                indSyncLinkF.setCusID(cusID);
                indSyncLinkList.add(indSyncLinkF);
            }
        }else if (tagname.equalsIgnoreCase(Individual.KEY_IndiName)) {
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + Individual.KEY_IndiName);
            individual.setIndiName(text);
        }/*else if (tagname.equalsIgnoreCase(ExImportContract.indiFIdIndi)) {
            indSyncLinkF = new Individual.syncLink();
            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + ExImportContract.indiFIdIndi);
            indSyncLinkF.setIndiFID(text);
        }else if (tagname.equalsIgnoreCase(ExImportContract.cusIdIndi)) {
            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + ExImportContract.cusIdIndi);
            indSyncLinkF.setCusID(text);
            indSyncLinkFlist.add(indSyncLinkF);
        }*/else if (tagname.equalsIgnoreCase(Individual.KEY_IsCons)) {
            Log.d("ensaf::::::::", TAG + " endTagSyncFile_AUTH> add " + text + " to : " + Individual.KEY_IsCons);
            individual.setIsCons(text);
        }

    }


    public List<wpt> parse(InputStream is){
        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
        CusAccountRepo cusAccountRepo = new CusAccountRepo();
        GPointRepo gPointRepo = new GPointRepo();
        Indi_CoopRepo indi_coopRepo = new Indi_CoopRepo();
        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
        IndividualRepo individualRepo = new IndividualRepo();
        IndividualRepo.syncLink syncLinkRepo = new IndividualRepo.syncLink(true);
        PhoneNumRepo phoneNumRepo = new PhoneNumRepo();
        TendRepo tendRepo = new TendRepo();
        EnsafQueryExport ensafQueryExport = new EnsafQueryExport();
        int insertedIndiId = 0;
        try{
            String cusID = null;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(EnsafQueryExport.customerID)) {
                            // initiate

                        }else if (tagname.equalsIgnoreCase(Cons_Phase.TABLE)) {
                            // create a new instance of ConstructionCustomer
                            cons_phase = new Cons_Phase();
                            Log.d("ensaf::::::::", TAG + "> initiate : " + Cons_Phase.TABLE);
                        }else if (tagname.equalsIgnoreCase(CusAccount.TABLE)){
                            // create a new instance of ConstructionPhase
                            cusAccount = new CusAccount();
                            Log.d("ensaf::::::::", TAG + "> initiate : " + CusAccount.TABLE);
                        }else if (tagname.equalsIgnoreCase(GPoint.TABLE)){
                            // create a new instance of Constructions
                            gPoint = new GPoint();
                            Log.d("ensaf::::::::", TAG + "> initiate : " + GPoint.TABLE);
                            indi_geop = new Indi_Geop();
                            Log.d("ensaf::::::::", TAG + "> initiate : " + Indi_Geop.TABLE);
                        }else if (tagname.equalsIgnoreCase(Indi_Coop.TABLE)){
                            // create a new instance of Customers
                            indi_coop = new Indi_Coop();
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.TABLE)){
                            // create a new instance of Follow
                            this.indi_geop = new Indi_Geop();
                        }else if (tagname.equalsIgnoreCase(Individual.TABLE)){
                            // create a new instance of PhoneNumber
                            indSyncLinkList.clear();
                            this.individual = new Individual();
                            this.indSyncLinkF = new Individual.syncLink();
                            Log.d("ensaf::::::::", TAG + "> initiate : " + Individual.TABLE);
                        }else if (tagname.equalsIgnoreCase(PhoneNum.TABLE)){
                            // create a new instance of Points
                            phoneNum = new PhoneNum();
                        }else if (tagname.equalsIgnoreCase(Tend.TABLE)){
                            // create a new instance of Region
                            this.tend = new Tend();
                        }else if (tagname.equalsIgnoreCase("wpt")){
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
                        if (tagname.equalsIgnoreCase(EnsafQueryExport.customerID)) {
                            // initiate
                            cusID = text;
                            Log.d("ensaf::::::::", TAG + " >CustomerID : " + EnsafQueryExport.customerID +  " : " +
                                text);
                        }else if (tagname.equalsIgnoreCase(Cons_Phase.TABLE)&&cons_phase!=null) {
                            // insert cons_phase
                            if (cons_phaseRepo.insert(cons_phase)>0){
                                Log.d("ensaf::::::::", TAG + "> insert data to  : " + Cons_Phase.TABLE);
                            }else {
                                Log.d("ensaf::::::::", TAG + "> NOT insert data to  : " + Cons_Phase.TABLE);
                            }

                        } else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_ID_Cons_Phase)&&cons_phase!=null) {
                            cons_phase.setID_Cons_Phase(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Cons_Phase.KEY_ID_Cons_Phase);
                        } else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_IndID)&&cons_phase!=null) {
                            //cons_phase.setIndID(text);
                            cons_phase.setIndID(ensafQueryExport.indiFromIndiF(text, cusID));
                            Log.d("ensaf::::::::", TAG + "> add " + ensafQueryExport.indiFromIndiF(text, cusID) + " to : " + Cons_Phase.KEY_IndID);
                        }else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_Phase)&&cons_phase!=null) {
                            cons_phase.setPhase(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Cons_Phase.KEY_Phase);
                        }else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_PhaseDate)&&cons_phase!=null) {
                            cons_phase.setPhaseDate(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Cons_Phase.KEY_PhaseDate);
                        //#############
                        }else if (tagname.equalsIgnoreCase(CusAccount.TABLE)) {
                            // insert cusAccount
                            cusAccountRepo.insert(cusAccount);
                            Log.d("ensaf::::::::", TAG + "> insert data to  : " + CusAccount.TABLE);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_ID_Cus)) {
                            cusAccount.setID_Cus(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + CusAccount.KEY_ID_Cus);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_IndID)) {
                            cusAccount.setIndID(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + CusAccount.KEY_IndID);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_AccountName)) {
                            cusAccount.setAccountName(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + CusAccount.KEY_AccountName);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_PassWord)) {
                            cusAccount.setPassWord(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + CusAccount.KEY_PassWord);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_IsAct)) {
                            cusAccount.setIsAct(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + CusAccount.KEY_IsAct);
                        //#############
                        }else if (tagname.equalsIgnoreCase(GPoint.TABLE)) {
                            // insert gPoint
                            gPointRepo.insert(gPoint);
                            Log.d("ensaf::::::::", TAG + "> insert data to  : " + GPoint.TABLE);
                            if (indi_geop!=null){
                                indi_geopRepo.insert(indi_geop);
                                Log.d("ensaf::::::::", TAG + "> insert data to  : " + Indi_Geop.TABLE);
                            }
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_IDGeop)) {
                            gPoint.setIDGeop(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + GPoint.KEY_IDGeop);
                            if (indi_geop!=null){
                                indi_geop.setGeopID(text);
                                Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Indi_Geop.KEY_GeopID);
                            }
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.KEY_IndiID)) {
                            ///indi_geop.setIndiID(text);
                            indi_geop.setIndiID(ensafQueryExport.indiFromIndiF(text, cusID));
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Indi_Geop.KEY_IndiID);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_Lat)) {
                            gPoint.setLat(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + GPoint.KEY_Lat);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_Lon)) {
                            gPoint.setLon(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + GPoint.KEY_Lon);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_IsSolo)) {
                            gPoint.setIsSolo(text);
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + GPoint.KEY_IsSolo);

                        }else if (tagname.equalsIgnoreCase(Indi_Coop.TABLE)) {
                            // insert indi_coop
                            indi_coopRepo.insert(indi_coop);
                        }else if (tagname.equalsIgnoreCase(Indi_Coop.KEY_ID_Indi_Co)) {
                            indi_coop.setID_Indi_Co(text);
                        }else if (tagname.equalsIgnoreCase(Indi_Coop.KEY_FirstPartID)) {
                            indi_coop.setFirstPartID(text);
                        }else if (tagname.equalsIgnoreCase(Indi_Coop.KEY_SecondPartID)) {
                            indi_coop.setSecondPartID(text);
                        }else if (tagname.equalsIgnoreCase(Indi_Coop.KEY_Title)) {
                            indi_coop.setTitle(text);

                        }else if (tagname.equalsIgnoreCase(Indi_Geop.TABLE)) {
                            // insert indi_geop
                            indi_geopRepo.insert(this.indi_geop);
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.KEY_ID_Indi_Geop)) {
                            this.indi_geop.setID_Indi_geop(text);
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.KEY_IndiID)) {
                            //this.indi_geop.setIndiID(text);
                            this.indi_geop.setIndiID(ensafQueryExport.indiFromIndiF(text, cusID));
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.KEY_GeopID)) {
                            this.indi_geop.setGeopID(text);

                        }else if (tagname.equalsIgnoreCase(Individual.TABLE)) {
                            insertedIndiId = individualRepo.insert(individual);
                            if (insertedIndiId > 0){
                                Log.d("ensaf::::::::", TAG + " : " + insertedIndiId + " > is inserted  : " + Individual.TABLE);
                                String insertedIndividualId = String.valueOf(insertedIndiId);
                                Log.d("ensaf::::::::", TAG + "> setIndiID " + insertedIndividualId + " to : " + "indSyncLinkF");
                                indSyncLinkF.setIndiID(insertedIndividualId);

                                for (int i = 0; i< indSyncLinkList.size() ; i++){
                                    indSyncLinkList.get(i).setIndiID(insertedIndividualId);
                                    if (syncLinkRepo.insert(indSyncLinkList.get(i))>0){
                                        Log.d("ensaf::::::::", TAG + "> inserted data to  : " + Individual.syncLink.TABLE_F +
                                                " id : " + indSyncLinkList.get(i).getIndiID());
                                    }
                                }
                            }else{
                                Log.d("ensaf::::::::", TAG + "> NOT inserted data to  : " + Individual.TABLE);
                            }
                        }else if (tagname.equalsIgnoreCase(Individual.KEY_ID_Indi)) {
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Individual.KEY_ID_Indi);
                            //individual.setID_Indi(text);
                            indSyncLinkF.setIndiFID(text);
                            indSyncLinkF.setCusID(cusID);
                            indSyncLinkList.add(indSyncLinkF);
                        }else if (tagname.equalsIgnoreCase(Individual.KEY_IndiName)) {
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Individual.KEY_IndiName);
                            individual.setIndiName(text);
                        }else if (tagname.equalsIgnoreCase(ExImportContract.indiFIdIndi)) {
                            indSyncLinkF = new Individual.syncLink();
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + ExImportContract.indiFIdIndi);
                            indSyncLinkF.setIndiFID(text);
                        }else if (tagname.equalsIgnoreCase(ExImportContract.cusIdIndi)) {
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + ExImportContract.cusIdIndi);
                            indSyncLinkF.setCusID(text);
                            indSyncLinkList.add(indSyncLinkF);
                        }else if (tagname.equalsIgnoreCase(Individual.KEY_IsCons)) {
                            Log.d("ensaf::::::::", TAG + "> add " + text + " to : " + Individual.KEY_IsCons);
                            individual.setIsCons(text);
                        }else if (tagname.equalsIgnoreCase(PhoneNum.TABLE)) {
                            // insert phoneNum
                            phoneNumRepo.insert(phoneNum);
                        }else if (tagname.equalsIgnoreCase(PhoneNum.KEY_ID_Phone)) {
                            phoneNum.setID_Phone(text);
                        }else if (tagname.equalsIgnoreCase(PhoneNum.KEY_IndiID)) {
                            phoneNum.setIndiID(text);
                        }else if (tagname.equalsIgnoreCase(PhoneNum.KEY_Num)) {
                            phoneNum.setNum(text);

                        }else if (tagname.equalsIgnoreCase(Tend.TABLE)) {
                            // insert tend
                            tendRepo.insert(this.tend);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_ID_Tend)) {
                            this.tend.setID_Tend(text);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_Ind1ID)) {
                            this.tend.setInd1ID(text);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_Ind2ID)) {
                            this.tend.setInd2ID(text);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_Title)) {
                            this.tend.setTitle(text);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_Detail)) {
                            this.tend.setDetail(text);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_TendDate)) {
                            this.tend.setTendDate(text);
                        }else if (tagname.equalsIgnoreCase(Tend.KEY_IsChecked)) {
                            this.tend.setIsChecked(text);
                        }else if (tagname.equalsIgnoreCase("wpt")){
                            // add wpt object to list
                            wpts.add(wpt);
                        }else if (tagname.equalsIgnoreCase("name")) {
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
        }catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return wpts;
    }
}
