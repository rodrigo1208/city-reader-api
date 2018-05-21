# API para leitura e insersão de cidades

### Tecnologias

 - Java Spark
 - Gradle
 - Google Gson

### Rotas

  - GET - /cities (Retorna todas as cidades)
  - POST - /cities (Adiciona uma nova cidade)
    - Exemplo de json
    > `{
    	"ibge_id": 5555555,
    	"uf": "JS",
    	"name": "Alta Floresta D'Oeste",
    	"capital": false,
    	"lon": -61.9998238963,
    	"lat": -11.9355403048,
    	"no_accents": "Alta Floresta D'Oeste",
    	"alternative_names": "",
    	"microregion": "Cacoal",
    	"macroregion": "Leste Rondoniense"
    }`
  - DELETE - /cities/:ibge_id (Deleta uma cidade de acordo com o id)
  - GET - /cities/search/capitals (Retorna todas as capitais
ordenadas por nome)
  - GET - /cities/search/distance (Retorna a maior distancia entre cidades)
  - GET - /cities/search/qtd-total (Retorna a quantidade de cidades)
  - GET - /cities/filter/param?queryParam (Retorna as cidades filtradas pela coluna)
  - GET - /cities/filter/column/:column (Retorna a quantidade de itens na coluna)
  - GET - /uf/greater (Retorna Estado com mais cidades)
  - GET - /uf/lower (Retorna Estado com menos cidades)
  - GET - /uf/cities-qtd (Retorna a quantidade de cidades por Estado)
  - GET - /uf/cities-name?queryParam (Retorna o nome das cidades de acotdo com o UF)

### Sistema

É necessário executar o Main e o servidor vai rodar na porta 4567.
