package sample.server;

import java.sql.*;

public class SQLiteJDBC {

   private static Connection db;
   private static Statement stmt;
   private static ResultSet rs;

   private static String userName;
   private static String id;
   private static int games;
   private static int  wins;
   private static int losses;

   //SETTERS AND GETTERS
   public static String getId() {
      return id;
   }

   public static int getGames() {
      return games;
   }

   public static int getWins() {
      return wins;
   }

   public static int getLosses() {
      return losses;
   }

   public static String getUserName() {
      return userName;
   }


   //CONSTRUCTOR
   public static void startDB(String name) {

      db = null;
      stmt = null;
      String dbName = "Group14_ludu";
      userName = name;

      try {
         dbConnect(dbName);
         //stmt.executeUpdate("DROP TABLE IF EXISTS USERS;");
         loadUser();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         //System.exit(0);
      }
   }

   //DB_CONNECT
   private static void dbConnect(String dbName) throws SQLException, ClassNotFoundException {
      Class.forName("org.sqlite.JDBC");
      db = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
      //System.out.println("Opened database successfully");
      stmt = db.createStatement();
   }

   public static void loadUser() {
      if(db==null){
         loadUserNoDB();
         return;
      }
      try {
         rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME ='"+userName+"';");
         //System.out.println("Load user successful");

         id = rs.getString("id");
         //userName = rs.getString("username");
         games  = rs.getInt("games");
         wins = rs.getInt("wins");
         losses = rs.getInt("losses");

      } catch (SQLException throwables) {
         //throwables.printStackTrace();
         //System.err.println( throwables.getClass().getName() + ": " + throwables.getMessage() );
         //IF CANNOT LOAD USER
         try {
            dbCrtUserTb();
         } catch (Exception e) {
            //e.printStackTrace();
         }finally {
            id = "p_"+userName+145;
            games=wins=losses=0;
            dbInsField(id,userName,games,wins,wins);
         }
      }
   }

   private static void loadUserNoDB(){
      id = "p_"+userName+"145";
      games  = 0;
      wins = 0;
      losses = 0;
   }

   public static void dbInsField(String id, String name, int g, int win, int loss) {

      try{
         String sql = "INSERT INTO USERS (ID,USERNAME,GAMES,WINS,LOSSES) " +
                 "VALUES ('"+id+"', '"+name+"', "+g+","+ win +","+ loss +");";
         //System.out.println(id);
         stmt.executeUpdate(sql);
         //System.out.println("Records created successfully");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   private static void dbCrtUserTb() throws SQLException {
      String sql = "CREATE TABLE USERS (" +
              " ID TEXT PRIMARY KEY  NOT NULL," +
              " USERNAME TEXT NOT NULL, " +
              " GAMES INT, " +
              " WINS INT, " +
              " LOSSES INT)";

      stmt.executeUpdate(sql);
      //System.out.println("Table created successfully");
   }

   //DB UPDATE OPERATIONS
   public static void hasPlayed() {
      games++;
      update("GAMES",games);//WRITE TO DB
   }
   public static void hasWon(){
      wins++;
      update("WINS",wins);
      //System.out.println(wins);
   }
   public static void haslost(){
      losses++;
      update("LOSSES",losses);
      //System.out.println(losses);
   }
   public static void setUserName(String name) {
      if(!userName.equals(name)){
         userName = name;
         update("USERNAME",userName);
      }
   }
   //HANDLE DUPLICATE USERNAME ERROR

   private static void update(String key, String value) {
      if(db==null)return;
         try{
            String sql = "UPDATE USERS SET "+ key +" = '"+value+"' WHERE ID='"+ id +"';";
            stmt.executeUpdate(sql);
            //db.commit();
            //System.out.println("Update Successful");
         } catch (SQLException throwables) {
            throwables.printStackTrace();
         }
   }

   private static  void update(String key, int value){
      if(db==null)return;
      try{
         String sql = "UPDATE USERS SET "+ key +"= "+value+" WHERE ID='"+ id +"';";
         stmt.executeUpdate(sql);
         //db.commit();
         //System.out.println("Update Successful");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   public static void delete() {
      if(db==null)return;
      try {
         stmt.close();
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }finally {
         try {
            db.close();
         } catch (SQLException throwables) {
            throwables.printStackTrace();
         }
      }
   }
}