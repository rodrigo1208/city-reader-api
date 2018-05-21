package br.com.rodrigocardoso.endpoints;

import br.com.rodrigocardoso.database.FileStream;
import br.com.rodrigocardoso.models.City;
import br.com.rodrigocardoso.utils.CityUtils;
import br.com.rodrigocardoso.utils.Response;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;
import static br.com.rodrigocardoso.utils.JsonUtils.*;

/**
 * Created by rodri on 20/05/2018.
 */
public class CitiesEndpoint implements IEndpoint {

    private final String URL = "/cities";
    private final String DB_URL = "src/main/database/city_db.csv";
    private final Map<String, String> filters = new HashMap<>();

    public CitiesEndpoint() {
        filters.put("ibge_id", "ibgeId");
        filters.put("uf", "uf");
        filters.put("name", "name");
        filters.put("capital", "capital");
        filters.put("lon", "longitude");
        filters.put("lat", "latitude");
        filters.put("no_accents", "noAccents");
        filters.put("alternative_names", "alternativeNames");
        filters.put("microregion", "microregion");
        filters.put("macroregion", "macroregion");
    }


    @Override()
    public void publish() {

        post(URL, "application/json", (req, res) -> {
            Response<City> response = new Response<>();
            City city = fromJson(req.body(), City.class);
            FileStream.write(DB_URL, dw -> {
                try {
                    dw.append(city.toFile());
                    response.set(200, "Cadastrado com sucesso", city);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.set(500, "Erro no cadastro", null);
                }
            });
            res.status(response.status);
            return response;
        }, json());

        delete(URL + "/:ibge_id", "application/json", (req, res) -> {
            Response<City> response = new Response<>();
            response.set(404, "Não encontrado", null);
            Integer ibgeId = Integer.parseInt(req.params("ibge_id"));

            FileStream.open(DB_URL, br -> {
                try {
                    br.readLine();
                    String actualLine;
                    while ((actualLine = br.readLine()) != null) {
                        String trimmedLine = actualLine.trim();
                        City city = City.tranform(trimmedLine);
                        if (city.getIbgeId().equals(ibgeId)) {
                            response.set(200, "Deletado", city);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (response.getResponse() != null) {
                FileStream.removeLineFromFile(DB_URL, response.getResponse().toFile());
            }

            res.status(response.status);
            return response;
        }, json());

        get(URL + "/:ibge_id", "application/json", (req, res) -> {

            Response<City> response = new Response<>();
            Integer ibgeId = Integer.parseInt(req.params("ibge_id"));

            FileStream.open(DB_URL, br -> {
                List<City> citiesFind = FileStream
                        .getLines(br)
                        .stream()
                        .map(City::tranform)
                        .filter(val -> val.getIbgeId().equals(ibgeId))
                        .collect(Collectors.toList());
                if (citiesFind.size() > 0) {
                    response.set(200, "Cidades", citiesFind.get(0));
                } else {
                    response.set(404, "Não foi encontrado uma cidade", new City());
                }
            });
            res.status(response.status);
            return response;
        }, json());

        get(URL, "application/json", (req, res) -> {

            Response<List<City>> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                List<City> cities = FileStream
                        .getLines(br)
                        .stream()
                        .map(City::tranform)
                        .collect(Collectors.toList());
                response.set(200, "Cidades", cities);
            });

            res.status(response.getStatus());
            return response;
        }, json());

        get(URL + "/search/capitals", "application/json", (req, res) -> {
            Response<List<City>> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                List<City> capitals = FileStream
                        .getLines(br)
                        .stream()
                        .map(City::tranform)
                        .filter(city -> city.getCapital())
                        .sorted(Comparator.comparing(val -> val.getName()))
                        .collect(Collectors.toList());

                response.set(200, "Capitais ordenadas", capitals);
            });

            res.status(response.status);
            return response;
        }, json());

        get(URL + "/filter/param", "application/json", (req, res) -> {
            Response<List<City>> response = new Response<>();
            FileStream.open(DB_URL, br -> {

                List<String> lines = FileStream.getLines(br);
                List<City> filteredValue = new ArrayList<>();
                req.queryMap()
                        .toMap()
                        .forEach((key, value) -> {
                            String val = req.queryParams(key);
                            if (filters.containsKey(key)) {
                                try {
                                    lines
                                            .stream()
                                            .map(City::tranform)
                                            .forEach(city -> {
                                                try {
                                                    Field field = City.class.getDeclaredField(filters.get(key));
                                                    field.setAccessible(true);
                                                    String fieldVal = field.get(city).toString();
                                                    if (fieldVal.toLowerCase().contains(val.toLowerCase())) {
                                                        filteredValue.add(city);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                response.set(200, "Cidades filtradas", filteredValue);

            });

            res.status(response.status);
            return response;
        }, json());

        get(URL + "/filter/column/:column", "application/json", (req, res) -> {
            Response<Object> response = new Response<>();
            String column = req.params("column");
            Set<String> values = new HashSet<>();
            if (filters.containsKey(column)) {

                FileStream.open(DB_URL, br -> {

                    List<String> lines = FileStream.getLines(br);

                   lines
                            .stream()
                            .map(City::tranform)
                            .forEach(val -> {
                                try {
                                    Field field = City.class.getDeclaredField(filters.get(column));
                                    field.setAccessible(true);
                                    values.add(field.get(val).toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                });

            }
            response.set(200, "Response", values.size());
            return response;
        }, json());

        get(URL + "/search/qtd-total", "application/json", (req, res) -> {
            Response<Integer> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                response.set(200, "Quantidade de registros", FileStream.getLines(br).size());
            });

            res.status(response.status);
            return response;
        }, json());

        get(URL + "/search/distance", "application/json", (req, res) -> {
            Response<Map<String, String>> response = new Response<>();

            FileStream.open(DB_URL, br -> {
                List<City> cities =  FileStream.getLines(br)
                        .stream()
                        .map(City::tranform)
                        .collect(Collectors.toList());

                Map<String, Double> distance = new HashMap<>();
                Map<String, String> citiesMap = new HashMap<>();

                distance.put("distance", 0.0);

                cities.forEach(citie1 -> {
                    cities.forEach(citie2 -> {
                        double value = CityUtils
                                .distance(
                                        citie1.getLatitude(),
                                        citie1.getLongitude(),
                                        citie2.getLatitude(),
                                        citie2.getLongitude(),
                                        'K');

                        if (value > distance.get("distance")) {
                            distance.put("distance", value);
                            citiesMap.put("citie1", citie1.getNoAccents());
                            citiesMap.put("citie2", citie2.getNoAccents());
                        }
                    });
                });

                citiesMap.put("distance", distance.get("distance").toString());
                response.set(200, "Maior distancia", citiesMap);

            });

            return response;
        }, json());

    }

}
