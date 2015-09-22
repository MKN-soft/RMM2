package teamproject.rmm2.Helpers;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.List;

import teamproject.rmm2.MainActivity;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.models.CalendarRow;
import teamproject.rmm2.models.HabitRow;

/**
 * Created by Marcin on 2015-09-09.
 * No public setters because I want only method calls and getting data. No setting antything "by hand"
 */
public class Statistics {
    private DbHelper dbHelper;
    private final long DAY = 24*3600;

    private HabitRow habitRow;

    private String tytulNawyku;
    private int iloscNawykow;
    private int najlepszaPassa;
    private float sredniaDlugoscCiagu;
    private float procent_powodzen;
    private int nawykID;


    public Statistics (Context context, String habitTitle){
        this.dbHelper = new DbHelper(context);

        setTytulNawyku(habitTitle);
        habitRow = dbHelper.getHabit(tytulNawyku);  //dane pobrane z bazy na potrzeby liczenia statystyk

        searchForHabitID();
        countDates();
        countPercentage();
        streakStats();

    }

    private void setTytulNawyku(String tytulNawyku) {
        this.tytulNawyku = tytulNawyku;
    }

    private void setIloscNawykow(int iloscNawykow) {
        this.iloscNawykow = iloscNawykow;
    }

    private void setNajlepszaPassa(int najlepszaPassa) {
        this.najlepszaPassa = najlepszaPassa;
    }

    private void setNawykID(int nawykID) {
        this.nawykID = nawykID;
    }

    private void setProcent_powodzen(float procent_powodzen) {
        this.procent_powodzen = procent_powodzen;
    }

    private void setSredniaDlugoscCiagu(float sredniaDlugoscCiagu) {
        this.sredniaDlugoscCiagu = sredniaDlugoscCiagu;
    }

    public int getIloscNawykow() {
        return iloscNawykow;
    }

    public float getNajlepszaPassa() {
        return najlepszaPassa;
    }

    public int getNawykID() {
        return nawykID;
    }

    public float getProcent_powodzen() {
        return procent_powodzen;
    }

    public float getSredniaDlugoscCiagu() {
        return sredniaDlugoscCiagu;
    }

    public String getTytulNawyku() {
        return tytulNawyku;
    }

    /**
     * Liczy iloscNawykow pobierając dane z CALENDAR dla nawyku o tytule pdoanym w konstruktorze.
     */
    private void countDates(){
        setIloscNawykow(dbHelper.getDateCountForHabit(tytulNawyku));
    }

    /**
     * liczy najlepsza_passa oraz srednia_dlugosc_ciagu dla nawyku o tytule z konstruktora
     */
    private void streakStats(){
        //Get list in ascending order
        List<CalendarRow> calendarRowList = dbHelper.getDatesForHabit(tytulNawyku);

        if(calendarRowList == null) {
            setNajlepszaPassa(0);
            setSredniaDlugoscCiagu(0);
            return;
        }

        long time=0, aux=0;
        int streak =0, longestStreak=0;
        int streakCount =0;





        for(CalendarRow row: calendarRowList){
            int id = row.getId();
            //po resecie streak wiadomo ze to kolejna passa (na starcie tez jest 0)
            if(streak == 0){
                streakCount++;
            }

            //jesli pierwszy rekord
            if(time == 0){
                time = row.getTime();
                streak++;
            }
            //jesli kolejny rekord
            else{
                aux = time;
                time = row.getTime();
                //jesli odstep czasu taki jak powinien byc oraz stan == "1"
                if(row.getState() == 1 && time - aux == DAY * habitRow.getFrequency() * habitRow.getPeriod()){
                    streak++;
                }
                else{
                    longestStreak = streak;
                    streak = 0;
                }
            }
        }

        //sprawdzamy czy ostatnia passa nie jest przypadkiem wieksza od znanej najwiekszej
        if (streak > longestStreak){
            longestStreak = streak;
        }

        setNajlepszaPassa(longestStreak);
        setSredniaDlugoscCiagu((float)dbHelper.getSuccessDateCountForHabit(tytulNawyku)/(float)streakCount);  // ilosc dat z sukcesami/ ilosc pass

    }

    /**
     * pobiera ID nawyku po tytule z konstruktora
     * @return int
     */
    private void searchForHabitID(){
        HabitRow habitRow = dbHelper.getHabit(tytulNawyku);
        setNawykID(habitRow.getId());
    }

    /**
     * Liczy procent_powodzen dla nawyku o tytule z konstruktora
     * wzor:
     * wykonane/(wykonane+niewykonane)*100%
     */
    private void countPercentage(){
        float percentage=0;


        int successCount = dbHelper.getSuccessDateCountForHabit(tytulNawyku);
        int totalCount = dbHelper.getDateCountForHabit(tytulNawyku);

        if(totalCount == 0){
            setProcent_powodzen(0);
            return;
        }

        percentage = ((float)successCount/(float)totalCount) * 100;
        setProcent_powodzen(percentage);
    }

}
