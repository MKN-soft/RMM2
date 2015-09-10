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
    private final long DAY = 24*3600*1000;

    private HabitRow habitRow;

    private String tytulNawyku;
    private int iloscNawykow;
    private int najlepszaPassa;
    private int sredniaDlugoscCiagu;
    private float procent_powodzen;
    private int nawykID;

    //TODO all the neccessary methods

    public Statistics (Context context, String habitTitle){
        this.dbHelper = new DbHelper(context);

        setTytulNawyku(habitTitle);

        habitRow = dbHelper.getHabit(tytulNawyku);
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

    private void setSredniaDlugoscCiagu(int sredniaDlugoscCiagu) {
        this.sredniaDlugoscCiagu = sredniaDlugoscCiagu;
    }

    public int getIloscNawykow() {
        return iloscNawykow;
    }

    public int getNajlepszaPassa() {
        return najlepszaPassa;
    }

    public int getNawykID() {
        return nawykID;
    }

    public float getProcent_powodzen() {
        return procent_powodzen;
    }

    public int getSredniaDlugoscCiagu() {
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
    private void bestStreak(){
        //TODO liczenie sredniej dlugosci ciagu
        //TODO check & test
        //Get list in ascending order
        List<CalendarRow> CalendarRowList = dbHelper.getDatesForHabit(tytulNawyku);

        long time=0, aux=0;
        int streak =0, longestStreak=0;

        for(CalendarRow row: CalendarRowList){
            //jesli pierwszy rekord
            if(time == 0){
                time = row.getTime();
                streak++;
            }
            //jesli kolejny rekord
            else{
                aux = time;
                time = row.getTime();
                //jesli odstep czasu taki jak powinien byc
                if(time - aux == DAY * habitRow.getFrequency() * habitRow.getPeriod()){
                    streak++;
                }
                else{
                    longestStreak = streak;
                    streak = 0;
                }
            }
        }

        if (streak > longestStreak){
            longestStreak = streak;
        }

        setNajlepszaPassa(longestStreak);

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
//        TODO

        float percentage=0;

        percentage = dbHelper.getSuccessDateCountForHabit(tytulNawyku) / dbHelper.getDateCountForHabit(tytulNawyku);
        percentage *= 100;
        setProcent_powodzen(percentage);
    }

}
