package br.com.rodrigocardoso.endpoints;

import br.com.rodrigocardoso.database.FileStream;
import br.com.rodrigocardoso.models.City;
import br.com.rodrigocardoso.models.UF;
import br.com.rodrigocardoso.utils.Response;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.rodrigocardoso.utils.JsonUtils.json;
import static spark.Spark.get;

/**
 * Created by rodri on 21/05/2018.
 */
public class UFEndpoint implements IEndpoint {

    private final String URL = "/uf";
    private final String DB_URL = "src/main/database/city_db.csv";

    @Override
    public void publish() {

        get(URL + "/greater", "application/json", (req, res) -> {
            Response<UF> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                List<String> lines = FileStream.getLines(br);
                Map<String, List<City>> citiesMap = lines
                        .stream()
                        .map(City::tranform)
                        .collect(Collectors.groupingBy(City::getUf));

                UF compare = new UF();
                response.set(200, "Estado com mais cidades", compare);

                citiesMap.forEach((uf, cities) -> {
                    UF newBigger = new UF()
                            .setName(uf)
                            .setQtdCities(cities.size());
                    if (newBigger.getQtdCities() > response.getResponse().getQtdCities()) {
                        response.setResponse(newBigger);
                    }
                });
            });

            res.status(response.status);
            return response;
        }, json());

        get(URL + "/lower", "application/json", (req, res) -> {
            Response<UF> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                List<String> lines = FileStream.getLines(br);
                Map<String, List<City>> citiesMap = lines
                        .stream()
                        .map(City::tranform)
                        .collect(Collectors.groupingBy(City::getUf));

                List<UF> ufs = citiesMap
                        .entrySet()
                        .stream()
                        .map(set -> new UF(set.getKey(), set.getValue()))
                        .sorted(Comparator.comparing(val -> val.getQtdCities()))
                        .collect(Collectors.toList());

                response.set(200, "Estado com menos cidades", ufs.get(0));
            });

            res.status(response.status);
            return response;
        }, json());

        get(URL + "/cities-qtd", "application/json", (req, res) -> {
            Response<List<UF>> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                List<String> lines = FileStream.getLines(br);
                Map<String, List<City>> citiesMap = lines
                        .stream()
                        .map(City::tranform)
                        .collect(Collectors.groupingBy(City::getUf));

                List<UF> ufs = citiesMap
                        .entrySet()
                        .stream()
                        .map(set ->
                                new UF()
                                        .setName(set.getKey())
                                        .setQtdCities(set.getValue().size())
                        )
                        .sorted(Comparator.comparing(val -> val.getQtdCities()))
                        .collect(Collectors.toList());

                response.set(200, "Cidades por estado", ufs);
            });
            res.status(response.status);
            return response;
        }, json());

        get("uf/cities-name", "application/json", (req, res) -> {
            Response<List<String>> response = new Response<>();
            String uf = req.queryParams("uf");

            FileStream.open(DB_URL, br -> {
                List<String> citiesName = FileStream
                        .getLines(br)
                        .stream()
                        .map(City::tranform)
                        .filter(city -> city.getUf().equals(uf))
                        .map(City::getName)
                        .collect(Collectors.toList());

                response.set(200, "Cidades", citiesName);
            });

            res.status(response.status);
            return response;
        }, json());

    }
}
