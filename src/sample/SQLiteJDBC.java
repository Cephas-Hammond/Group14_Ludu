package sample;

import java.sql.*;

public class SQLiteJDBC {

   private Connection db;
   private Statement stmt;
   private static ResultSet rs;
   private static String dbName;

   private String userName;
   private String id;
   private int games;
   private int  wins;
   private int losses;
   private int player;

   //SETTERS AND GETTERS
   public String getId() {
      return id;
   }

   public int getGames() {
      return games;
   }

   public int getWins() {
      return wins;
   }

   public int getLosses() {
      return losses;
   }

   public String getUserName() {
      return userName;
   }

   public int getPlayer(){
      return player;
   }

   //CONSTRUCTOR
   public SQLiteJDBC(String name) {

      db = null;
      stmt = null;
      dbName = "Group14_ludu";
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
   public void dbConnect(String dbName) throws SQLException, ClassNotFoundException {
      Class.forName("org.sqlite.JDBC");
      db = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
      System.out.println("Opened database successfully");
      stmt = db.createStatement();
   }

   public void loadUser() {
      try {
         rs = stmt.executeQuery( "SELECT * FROM USERS WHERE USERNAME ='"+userName+"';");
         System.out.println("Load user successful");

         id = rs.getString("id");
         //userName = rs.getString("username");
         games  = rs.getInt("games");
         wins = rs.getInt("wins");
         losses = rs.getInt("losses");

      } catch (SQLException throwables) {
         //throwables.printStackTrace();
         //System.err.println( throwables.getClass().getName() + ": " + throwables.getMessage() );
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

   public void dbInsField(String id, String name, int g, int win, int loss) {

      try{
         String sql = "INSERT INTO USERS (ID,USERNAME,GAMES,WINS,LOSSES) " +
                 "VALUES ('"+id+"', '"+name+"', "+g+","+ win +","+ loss +");";
         System.out.println(id);
         stmt.executeUpdate(sql);
         //db.commit();
         System.out.println("Records created successfully");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   public void dbCrtUserTb() throws SQLException {
      String sql = "CREATE TABLE USERS (" +
              " ID TEXT PRIMARY KEY  NOT NULL," +
              " USERNAME TEXT NOT NULL, " +
              " GAMES INT, " +
              " WINS INT, " +
              " LOSSES INT)";

      stmt.executeUpdate(sql);
      System.out.println("Table created successfully");
   }

   //DB UPDATE OPERATIONS
   public void hasPlayed() {
      games++;
      update("GAMES",games);//WRITE TO DB
   }
   public void hasWon(){
      wins++;
      update("WINS",wins);
   }
   public void haslost(){
      losses++;
      update("LOSSES",losses);
   }
   public void setUserName(String name) {
      if(!userName.equals(name)){
         this.userName = name;
         update("USERNAME",userName);
      }
   }
   //HANDLE DUPLICATE USERNAME ERROR

   private void update(String key, String value) {
      try{
         String sql = "UPDATE USERS SET "+ key +" = '"+value+"' WHERE ID='"+ id +"';";
         stmt.executeUpdate(sql);
         //db.commit();
         System.out.println("Update Successful");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   private  void update(String key, int value){
      try{
         String sql = "UPDATE USERS SET "+ key +"= "+value+" WHERE ID='"+ id +"';";
         stmt.executeUpdate(sql);
         //db.commit();
         System.out.println("Update Successful");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   public void delete() {
      try {
         rs.close();
         stmt.close();
         db.close();
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }

   }
}