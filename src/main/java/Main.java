import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;

/**
 *
 * @author Unit 1
 */
public class Main {

  public static void main(String[] args) throws Exception {

    //Point to the DB file
    final String dbPath = new File("").getAbsolutePath() + "/db/example.db";

    //Create an object with the data
    UserModel userModel = new UserModel("USER NAME", "FIRST NAME", "LAST NAME",
        "EMAIL", "dasdaeEsdasd", 1);

    //Create a connection
    JdbcPooledConnectionSource connectionSource
        = new JdbcPooledConnectionSource("jdbc:sqlite:" + dbPath);

    //Create a table is one doesn't exist. tableName is taken from the model
    TableUtils.createTableIfNotExists(connectionSource, UserModel.class);

    //Create the DAO
    Dao<UserModel, Long> userDao
        = DaoManager.createDao(connectionSource, UserModel.class);

    //Insert the data in the table
    userDao.create(userModel);

    //Read from the table
    UserModel retrievedData = userDao.queryForId(1L);
    System.out.println("Initial data" + new Gson().toJson(retrievedData));

    //Update record
    retrievedData.setUsername("NEW USERNAME");
    userDao.update(retrievedData);
    retrievedData = userDao.queryForId(1L);
    System.out.println("Updated data" + new Gson().toJson(retrievedData));

    //Delete record
    userDao.delete(userModel);

    //Drop the table
    TableUtils.dropTable(userDao, false);

    connectionSource.close();
  }
}
