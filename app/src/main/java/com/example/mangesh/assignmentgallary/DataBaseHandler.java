package com.example.mangesh.assignmentgallary;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.sax.StartElementListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mangesh on 10/14/2017.
 */

class DataBaseHandler extends SQLiteOpenHelper {

    // Creating table for storing names in the database with 2 columns 'id' and 'name' resp.

    private String table_name = "Names";
    private String table_assignment = "Assignment";
    private String name_Col0 = "id";
    private String name_Col1 = "name";
    private String createtable_name = "CREATE TABLE "+table_name+"("+name_Col0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name_Col1+" TEXT)";

    /*
        Creating table for storing assignment info. with 5 columns
        A_Name, A_Sub,A_Status,A_Availability,A_Date
    */

    private String assign_name = "A_Name";
    private String assign_sub = "A_Sub";
    private String assign_status = "A_Status";
    private String assign_Availability = "A_Availability";
    private String assign_DeadLine = "A_DeadLine";
    private String createtable_assignment = "CREATE TABLE "+table_assignment+"("+assign_name+" TEXT,"+assign_sub+" TEXT,"+assign_status+" TEXT,"+assign_Availability+" TEXT,"+assign_DeadLine+" TEXT)";

    public String getAssignName(){
        return assign_name;
    }

    public DataBaseHandler(Context context) {
        super(context, "ASSIGNMENTS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createtable_assignment);
        db.execSQL(createtable_name);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+table_name+";");

        db.execSQL("DROP TABLE IF EXISTS "+table_assignment+";");
    }

    public void insertName(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(name_Col1,name);
        db.insert(table_name,null,value);
        db.close();

    }



    public boolean getcount(){

        String selectquery = "SELECT * FROM "+table_name+";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectquery,null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;

    }

    public String getname() {

        SQLiteDatabase db = this.getReadableDatabase();
        String seletquery = "SELECT * FROM "+table_name+";";
        Cursor c = db.rawQuery(seletquery,null);
        if(c.moveToFirst()){
            return c.getString(1);
        }else
            return null;

    }

    public void AddAssignment(String set_assign_name, String set_assign_sub, String set_assign_status, String set_assign_availability, String set_assign_deadline) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(assign_name,set_assign_name);
        values.put(assign_sub,set_assign_sub);
        values.put(assign_status,set_assign_status);
        values.put(assign_Availability,set_assign_availability);
        values.put(assign_DeadLine, set_assign_deadline);
        db.insert(table_assignment,null,values);
        db.close();

    }

    public ArrayList<AssignModel> getAllAssignments() {

        ArrayList<AssignModel> assignment;

        String seletquery = "select * from Assignment";

        Cursor c = null;

        String[] sub = new String[]{"CN"};
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery(seletquery,null);
        /*try{

            c = db.query(table_assignment,new String[]{assign_name,assign_sub,assign_status,assign_Availability},"A_Sub = CN",null,null,null,null);

        }catch (Exception e){
            return null;
        }*/

        assignment = new ArrayList<AssignModel>();

        if (c.moveToFirst()){

            do{
                AssignModel assignModel = new AssignModel(c.getString(c.getColumnIndex(assign_name)),c.getString(c.getColumnIndex(assign_sub)),c.getString(c.getColumnIndex(assign_status)),c.getString(c.getColumnIndex(assign_Availability)), c.getString(c.getColumnIndex(assign_DeadLine)));

                assignment.add(assignModel);
            }while (c.moveToNext());

        }

        return assignment;

    }


    public ArrayList<AssignModel> ShowSelectedAssign(String column_name, String getView_assign_contents) {

        ArrayList<AssignModel> assignment;

        String SelectQuery = "select * from Assignment where "+column_name+" = \""+getView_assign_contents+"\"";
        Cursor c = null;

        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(SelectQuery,null);

        assignment = new ArrayList<AssignModel>();

        while (c.moveToNext()){

            AssignModel assignModel = new AssignModel(c.getString(c.getColumnIndex(assign_name)),c.getString(c.getColumnIndex(assign_sub)),c.getString(c.getColumnIndex(assign_status)),c.getString(c.getColumnIndex(assign_Availability)), c.getString(c.getColumnIndex(assign_DeadLine)));

            assignment.add(assignModel);

        }

        return assignment;

    }

    public String[] getColumn(String colName){

        String[] column = new String[getAssignCount()];

        String getDatesQuery = "select "+colName+" from Assignment";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(getDatesQuery, null);

        int i=0;
        while (c.moveToNext()){

            column[i] = c.getString(0);
            i++;

        }

        return column;

    }

    public ArrayList getValueOfAt(String colName, String deadLine){

        ArrayList column = new ArrayList();

        String getDatesQuery = "select "+colName+" from Assignment where A_DeadLine = \""+deadLine+"\";";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(getDatesQuery, null);

        int i=0;
        while (c.moveToNext()){

            column.add(c.getString(0));

        }

        return column;

    }

    public int getAssignCount(String column_name, String getView_assign_contents) {

        String SelectQuery = "select * from Assignment where "+column_name+" = \""+getView_assign_contents+"\"";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(SelectQuery,null);

        return c.getCount();

    }

    public int getAssignCount() {

        String seletquery = "select * from Assignment";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(seletquery,null);

        return c.getCount();

    }

    public int getPendingAssignCount(){

        String selectQuery = "select * from Assignment where A_Status = \"Not written\";";
        Cursor c = null;
        SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(selectQuery, null);

        return c.getCount();

    }

    public boolean DeleteSelectedItem(String name){

        String deleteQuery = "delete from Assignment where A_Name = \""+name+"\";";
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.rawQuery(deleteQuery,null).moveToFirst();
        }catch (Exception e){return false;}
        return true;

    }

    public boolean updateAssignment(String name, String subjectToBeUpdated, String statusToBeUpdated, String availabilityToBeUpdated, String deadlineToBeUpdated){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(assign_sub, subjectToBeUpdated);
        values.put(assign_status,statusToBeUpdated);
        values.put(assign_Availability,availabilityToBeUpdated);
        values.put(assign_DeadLine, deadlineToBeUpdated);

        try {

            db.update(table_assignment, values, assign_name + "=" + "'" + name + "'", null);

        }catch (Exception e){return false;}

        return true;
    }

}
