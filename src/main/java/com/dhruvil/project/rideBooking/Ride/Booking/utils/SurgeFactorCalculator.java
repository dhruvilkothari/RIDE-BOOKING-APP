package com.dhruvil.project.rideBooking.Ride.Booking.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

 public  class SurgeFactorCalculator {
     static HashMap<String, Double> surgeFactorMap = new HashMap<>();
     static ArrayList<String> factors = new ArrayList<>();
    public static void populateSurgeFactorMap(){

        factors.add("Male");
        factors.add("Female");
        factors.add("Android");
        factors.add("IOS");
        factors.add("Rainy");
        factors.add("Surge");
        for (String factor : factors) {
            surgeFactorMap.put(factor, Math.random()*2);
        }
    }
    public static double getSurgeFactor(){
        populateSurgeFactorMap();
        Random random = new Random();
        int value = random.nextInt(6);
        return surgeFactorMap.get(factors.get(value));
    }
}
