package br.com.rodrigocardoso.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodri on 18/05/2018.
 */
public class City {

    @SerializedName("ibge_id")
    private Integer ibgeId;
    private String uf;
    private String name;
    private Boolean capital;
    @SerializedName("lon")
    private Double longitude;
    @SerializedName("lat")
    private Double latitude;
    @SerializedName("no_accents")
    private String noAccents;
    @SerializedName("alternative_names")
    private String alternativeNames;
    private String microregion;
    private String macroregion;

    public Integer getIbgeId() {
        return ibgeId;
    }

    public City setIbgeId(Integer ibgeId) {
        this.ibgeId = ibgeId;
        return this;
    }

    public String getUf() {
        return uf;
    }

    public City setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getCapital() {
        return capital;
    }

    public City setCapital(Boolean capital) {
        this.capital = capital;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public City setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public City setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getNoAccents() {
        return noAccents;
    }

    public City setNoAccents(String noAccents) {
        this.noAccents = noAccents;
        return this;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public City setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
        return this;
    }

    public String getMicroregion() {
        return microregion;
    }

    public City setMicroregion(String microregion) {
        this.microregion = microregion;
        return this;
    }

    public String getMacroregion() {
        return macroregion;
    }

    public City setMacroregion(String macroregion) {
        this.macroregion = macroregion;
        return this;
    }

    public static City tranform(String line) {
        String[] split = line.split(",");
        return new City()
                .setIbgeId(Integer.parseInt(split[0]))
                .setUf(split[1])
                .setName(split[2])
                .setCapital(Boolean.parseBoolean(split[3]))
                .setLongitude(Double.parseDouble(split[4]))
                .setLatitude(Double.parseDouble(split[5]))
                .setNoAccents(split[6])
                .setAlternativeNames(split[7])
                .setMicroregion(split[8])
                .setMacroregion(split[9]);
    }

    public String toFile() {
        StringBuilder sb = new StringBuilder()
                .append(this.ibgeId).append(",")
                .append(this.uf).append(",")
                .append(this.name).append(",")
                .append(this.capital).append(",")
                .append(this.longitude).append(",")
                .append(this.latitude).append(",")
                .append(this.noAccents).append(",")
                .append(this.alternativeNames).append(",")
                .append(this.microregion).append(",")
                .append(this.macroregion);
        return sb.toString();
    }

}
