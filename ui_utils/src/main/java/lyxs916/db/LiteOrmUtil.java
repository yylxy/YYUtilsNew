package lyxs916.db;


import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;


public class LiteOrmUtil {

    public static  int dbVersion = 1;
    private static LiteOrm liteOrm;

    private LiteOrmUtil() {
    }

    public static void init(Application application,String name) {
        if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(application, name);
            config.debugged = true; // open the log
            config.dbVersion = dbVersion; // set database version
            config.onUpdateListener = null; // set database update listener
            liteOrm = LiteOrm.newSingleInstance(config);
        }
    }

    public static LiteOrm getLiteOrm() {
        return liteOrm;
    }

    public static void setDbVersion(int version){
        dbVersion=version;
    }
}
