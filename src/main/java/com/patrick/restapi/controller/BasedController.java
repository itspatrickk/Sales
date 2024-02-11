package com.patrick.restapi.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.Set;

@Configuration
public class BasedController {
    private static final Logger logger = LoggerFactory.getLogger(BasedController.class);

    public void displayObject(JSONObject jsonObject){
        try {
            Set<String> set = jsonObject.keySet();
            Iterator<String> iterator = set.iterator();

            logger.info(".......................................DISPLAY-OBJECT-START.......................................");
            while (iterator.hasNext()){
                Object obj = iterator.next();
                logger.info("..." +obj.toString() + ": \t" + jsonObject.getString((String) obj));
            }
            logger.info(".......................................DISPLAY-OBJECT-END.......................................");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
