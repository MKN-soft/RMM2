package teamproject.rmm2.Helpers;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import teamproject.rmm2.MainActivity;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.models.HabitRow;

/**
 * Created by Marcin on 2015-09-09.
 * No public setters because I want only method calls and getting data. No setting antything "by hand"
 */
public class Statistics {
    private DbHelper dbHelper;

    private String tytulNawyku;
    private int iloscNawykow;
    private int najlepszaPassa;
    private int sredniaDlugoscCiagu;
    private int procent_powodzen;
    private int nawykID;

    //TODO all the neccessary methods

    public Statistics (Context context, String habitTitle){
        this.dbHelper = new DbHelper(context);

        setTytulNawyku(habitTitle);
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

    private void setProcent_powodzen(int procent_powodzen) {
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

    public int getProcent_powodzen() {
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
     * liczy najlepsza_passa dla nawyku o tytule podanym w konstruktorze
     */
    private void bestStreak(){
        //TODO
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
    }

    /**
     * liczy srednia_dlugosc_ciagu dla nawyku o tytule z konstruktora
     */
    private void averageStreakLength(){
//        TODO
    }

}
