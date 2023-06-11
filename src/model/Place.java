/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author olivier
 */
public class Place {
    String id, name, province;
    
    public Place(){
        
    }
    
    public Place(String id,String name, String province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }
    
    public ArrayList<Place> getAll(Connection connection) {
        String query = "select * from place";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            
            ArrayList<Place> places = new ArrayList<Place>();
            while(result.next()){
                places.add(new Place(
                        result.getString("id"),
                        result.getString("name"),
                        result.getString("province")
                ));
            }
            
            return places;
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
