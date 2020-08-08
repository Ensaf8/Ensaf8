package com.parandak.ensaf8.DirectionManagement;

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


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlPullParserHandlerForEnsaf {


    private Cons_Phase cons_phase;
    private CusAccount cusAccount;
    private GPoint gPoint;
    private Indi_Coop indi_coop;
    private Indi_Geop indi_geop;
    private Individual individual;
    private PhoneNum phoneNum;
    private Tend tend;

    private String text;

    private List<wpt> wpts= new ArrayList<wpt>();
    private wpt wpt;

    public List<wpt> getWpts() {
        return wpts;
    }



    public List<wpt> parse(InputStream is){
        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
        CusAccountRepo constructionPhaseRepo = new CusAccountRepo();
        GPointRepo gPointRepo = new GPointRepo();
        Indi_CoopRepo indi_coopRepo = new Indi_CoopRepo();
        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
        IndividualRepo individualRepo = new IndividualRepo();
        PhoneNumRepo phoneNumRepo = new PhoneNumRepo();
        TendRepo tendRepo = new TendRepo();
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(Cons_Phase.TABLE)) {
                            // create a new instance of ConstructionCustomer
                            cons_phase = new Cons_Phase();
                        }else if (tagname.equalsIgnoreCase(CusAccount.TABLE)){
                            // create a new instance of ConstructionPhase
                            cusAccount = new CusAccount();
                        }else if (tagname.equalsIgnoreCase(GPoint.TABLE)){
                            // create a new instance of Constructions
                            gPoint = new GPoint();
                        }else if (tagname.equalsIgnoreCase(Indi_Coop.TABLE)){
                            // create a new instance of Customers
                            indi_coop = new Indi_Coop();
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.TABLE)){
                            // create a new instance of Follow
                            this.indi_geop = new Indi_Geop();
                        }else if (tagname.equalsIgnoreCase(Individual.TABLE)){
                            // create a new instance of PhoneNumber
                            this.individual = new Individual();
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
                        if (tagname.equalsIgnoreCase(Cons_Phase.TABLE)) {
                            // insert cons_phase
                            cons_phaseRepo.insert(cons_phase);
                        } else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_ID_Cons_Phase)) {
                            cons_phase.setID_Cons_Phase(text);
                        } else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_IndID)) {
                            cons_phase.setIndID(text);
                        }else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_Phase)) {
                            cons_phase.setPhase(text);
                        }else if (tagname.equalsIgnoreCase(Cons_Phase.KEY_PhaseDate)) {
                            cons_phase.setPhaseDate(text);

                        }else if (tagname.equalsIgnoreCase(CusAccount.TABLE)) {
                            // insert cusAccount
                            constructionPhaseRepo.insert(cusAccount);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_ID_Cus)) {
                            cusAccount.setID_Cus(text);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_IndID)) {
                            cusAccount.setIndID(text);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_AccountName)) {
                            cusAccount.setAccountName(text);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_PassWord)) {
                            cusAccount.setPassWord(text);
                        }else if (tagname.equalsIgnoreCase(CusAccount.KEY_IsAct)) {
                            cusAccount.setIsAct(text);

                        }else if (tagname.equalsIgnoreCase(GPoint.TABLE)) {
                            // insert gPoint
                            gPointRepo.insert(gPoint);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_IDGeop)) {
                            gPoint.setIDGeop(text);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_Lat)) {
                            gPoint.setLat(text);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_Lon)) {
                            gPoint.setLon(text);
                        }else if (tagname.equalsIgnoreCase(GPoint.KEY_IsSolo)) {
                            gPoint.setIsSolo(text);

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
                            this.indi_geop.setIndiID(text);
                        }else if (tagname.equalsIgnoreCase(Indi_Geop.KEY_GeopID)) {
                            this.indi_geop.setGeopID(text);

                        }else if (tagname.equalsIgnoreCase(Individual.TABLE)) {
                            // insert indi_geop
                            individualRepo.insert(this.individual);
                        }else if (tagname.equalsIgnoreCase(Individual.KEY_ID_Indi)) {
                            this.individual.setID_Indi(text);
                        }else if (tagname.equalsIgnoreCase(Individual.KEY_IndiName)) {
                            this.individual.setIndiName(text);
                        }else if (tagname.equalsIgnoreCase(Individual.KEY_IsCons)) {
                            this.individual.setIsCons(text);

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
