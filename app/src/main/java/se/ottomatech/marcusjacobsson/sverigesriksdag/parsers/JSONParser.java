package se.ottomatech.marcusjacobsson.sverigesriksdag.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberAssignmentPojo;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class JSONParser {
    public static ArrayList<MemberPojo> parseJSONObject(JSONObject json) {

        ArrayList<MemberPojo> members = new ArrayList<>();

        try {
            JSONObject personlista = json.getJSONObject("personlista");
            int hits = Integer.valueOf(personlista.getString("@hits"));

            for (int i = 0; i < hits; i++) {
                if (hits != 1) {
                    JSONArray person = personlista.getJSONArray("person");
                    MemberPojo member = new MemberPojo();
                    ArrayList<MemberAssignmentPojo> assignments = new ArrayList<>();
                    member.setYearBorn(person.getJSONObject(i).getString("fodd_ar"));
                    member.setSex(person.getJSONObject(i).getString("kon"));
                    member.setLastName(person.getJSONObject(i).getString("efternamn"));
                    member.setFirstName(person.getJSONObject(i).getString("tilltalsnamn"));
                    member.setParty(person.getJSONObject(i).getString("parti"));
                    member.setDistrict(person.getJSONObject(i).getString("valkrets"));
                    member.setStatus(person.getJSONObject(i).getString("status"));
                    member.setImageUrl(person.getJSONObject(i).getString("bild_url_192"));

                    JSONObject personuppgift = person.getJSONObject(i).getJSONObject("personuppgift");
                    //Kontrollera om uppgift är en Array eller Object
                    JSONArray uppgift = personuppgift.optJSONArray("uppgift");
                    //Uppgift är array
                    if(uppgift != null){
                        for (int y = 0; y < uppgift.length(); y++) {
                            String kod = uppgift.getJSONObject(y).getString("kod");
                            if (kod.equals("Officiell e-postadress")) {
                                member.setEmailAddress(uppgift.getJSONObject(y).getString("uppgift"));
                            } else if (kod.equals("Webbsida")) {
                                member.setWebsiteUrl(uppgift.getJSONObject(y).getString("uppgift"));
                            }
                        }
                    //Uppgift är Object
                    }else{
                        JSONObject uppgiftObjekt = person.getJSONObject(i).getJSONObject("personuppgift").getJSONObject("uppgift");
                        String kod = uppgiftObjekt.getString("kod");
                        if (kod.equals("Officiell e-postadress")) {
                            member.setEmailAddress(uppgiftObjekt.getString("uppgift"));
                        } else if (kod.equals("Webbsida")) {
                            member.setWebsiteUrl(uppgiftObjekt.getString("uppgift"));
                        }
                    }

                    JSONArray uppdrag = person.getJSONObject(i).getJSONObject("personuppdrag").getJSONArray("uppdrag");
                    for (int x = 0; x < uppdrag.length(); x++) {
                        MemberAssignmentPojo assignment = new MemberAssignmentPojo();
                        assignment.setRole(uppdrag.getJSONObject(x).getString("roll_kod"));
                        assignment.setType(uppdrag.getJSONObject(x).getString("typ"));
                        assignment.setStatus(uppdrag.getJSONObject(x).getString("status"));
                        assignment.setDtStart(uppdrag.getJSONObject(x).getString("from"));
                        assignment.setDtEnd(uppdrag.getJSONObject(x).getString("tom"));
                        assignment.setTask(uppdrag.getJSONObject(x).getString("uppgift"));
                        assignments.add(assignment);
                    }
                    member.setAssignmentPojos(assignments);
                    members.add(member);
                } else {
                    JSONObject person = personlista.getJSONObject("person");
                    MemberPojo member = new MemberPojo();
                    ArrayList<MemberAssignmentPojo> assignments = new ArrayList<>();
                    member.setYearBorn(person.getString("fodd_ar"));
                    member.setSex(person.getString("kon"));
                    member.setLastName(person.getString("efternamn"));
                    member.setFirstName(person.getString("tilltalsnamn"));
                    member.setParty(person.getString("parti"));
                    member.setDistrict(person.getString("valkrets"));
                    member.setStatus(person.getString("status"));
                    member.setImageUrl(person.getString("bild_url_max"));

                    JSONArray uppdrag = person.getJSONObject("personuppdrag").getJSONArray("uppdrag");
                    for (int x = 0; x < uppdrag.length(); x++) {
                        MemberAssignmentPojo assignment = new MemberAssignmentPojo();
                        assignment.setRole(uppdrag.getJSONObject(x).getString("roll_kod"));
                        assignment.setType(uppdrag.getJSONObject(x).getString("typ"));
                        assignment.setStatus(uppdrag.getJSONObject(x).getString("status"));
                        assignment.setDtStart(uppdrag.getJSONObject(x).getString("from"));
                        assignment.setDtEnd(uppdrag.getJSONObject(x).getString("tom"));
                        assignment.setTask(uppdrag.getJSONObject(x).getString("uppgift"));
                        assignments.add(assignment);
                    }
                    member.setAssignmentPojos(assignments);
                    members.add(member);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return members;
    }
}
