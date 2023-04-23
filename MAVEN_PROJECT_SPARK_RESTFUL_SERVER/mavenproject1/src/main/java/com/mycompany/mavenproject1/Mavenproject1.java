/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Administrator
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        spark.Spark.port(8000);
        spark.Spark.get("/path", (req, res) -> {
            
            User user = new User("duong", "2123123z");
            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(user);
            res.header("Content-Type", "application/json");
            
           return data;
        });
    }
}
