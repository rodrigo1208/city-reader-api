package br.com.rodrigocardoso.endpoints;

/**
 * Created by rodri on 20/05/2018.
 */
public class Initializer {

    public static void init() {
        new CitiesEndpoint().publish();
        new UFEndpoint().publish();
    }

}