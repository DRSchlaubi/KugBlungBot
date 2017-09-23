package net.Schlaubi.KuhBlungBot.util;


import net.dv8tion.jda.core.entities.User;

import java.sql.*;

public class MySQL {

    private static String password = SECRETS.password;
    private static Connection connection;

    public static void connect(){
        if(!isConnected()){
            try {
                String host = STATIC.HOST;
                String port = STATIC.PORT;
                String database = STATIC.DATABASE;
                String username = STATIC.USERNAME;
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                System.out.println("[KuhBlungBot] MySQL connected");
            } catch (SQLException e) {
                System.out.println("[KuhBlungBot] MySQL connection failed");
                e.printStackTrace();
            }
        }

    }
    private static boolean isConnected(){

        return (connection != null);

    }

    public static Connection getConnection() {
        return connection;
    }

    public static boolean isUserExsists(User user){

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM kuhblung WHERE discordid = ?");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createUser(User user){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `kuhblung`(`discordid`, `level`, `points`, `money`, `cookies`, `status`,`netdex`,`twitter`,`reddit`,`steam`,`twitch`,`twitter`) VALUES (?, '1', '0', '0', '0', 'Hey, I am using Discord','0','0','0','0','0','0')");
            ps.setString(1, user.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateValue(User user, String type, String value){

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE kuhblung SET " + type + " = '" + value +"' WHERE `discordid` = " + user.getId());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    public static String getValue(User user, String type){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM kuhblung WHERE `discordid` = ?");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getString(type);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }


}
