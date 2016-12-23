package stickybook.com.br.stickybook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lleo on 19/09/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String BD_NAME = "DB";
    private static int DV_VENSION = 1;

    public DBHelper(Context context) {

        super(context, BD_NAME , null, DV_VENSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreatePalavras());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
