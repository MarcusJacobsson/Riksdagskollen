package se.ottomatech.marcusjacobsson.sverigesriksdag.pojo;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class SpeechPojo {

    private String docTitle;
    private String docDate;
    private String sectionTitle;
    private String chamberActivity;
    private String speechNumber;
    private String speaker;
    private String party;
    private String speechText;
    private String line;

    public SpeechPojo(){}

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getChamberActivity() {
        return chamberActivity;
    }

    public void setChamberActivity(String chamberActivity) {
        this.chamberActivity = chamberActivity;
    }

    public String getSpeechNumber() {
        return speechNumber;
    }

    public void setSpeechNumber(String speechNumber) {
        this.speechNumber = speechNumber;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getSpeechText() {
        return speechText;
    }

    public void setSpeechText(String speechText) {
        this.speechText = speechText;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
