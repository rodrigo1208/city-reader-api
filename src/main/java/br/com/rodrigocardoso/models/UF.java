package br.com.rodrigocardoso.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 20/05/2018.
 */
public class UF {
    private String name;
    private List<City> cities;
    private Integer qtdCities;

    public UF() {
        this.cities = new ArrayList<>();
        this.qtdCities = 0;
    }

    public UF(String uf, List<City> cities) {
        this.name = uf;
        this.cities = cities;
        this.qtdCities = cities.size();
    }

    public String getName() {
        return name;
    }

    public UF setName(String name) {
        this.name = name;
        return this;
    }

    public List<City> getCitiex() {
        return cities;
    }

    public UF setCities(List<City> cities) {
        this.cities = cities;
        this.qtdCities = this.cities.size();
        return this;
    }

    public UF setQtdCities(Integer qtdCities) {
        this.qtdCities = qtdCities;
        return this;
    }

    public Integer getQtdCities() {
        return this.qtdCities;
    }
}
