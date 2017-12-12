# Java evaluation

This repos is a spring-boot based application use for simple technical evaluation.

## Design

The goal is to search on the [ghibli public api](https://ghibliapi.herokuapp.com/) some movies and manage your favorites.

### Retrieve movies

You need to find ghibli movies with a endpoints like `GET http://localhost:8080/movies?q=to`.

This endpoint is just a proxy on the public api.

### Add a movie to favorite

You should save a movie as your favorite on a mysql database with `POST http://localhost:8080/favorite`.

The body of this request should contain *only* the id of the movie.

Your favorites should contains thoses informations in database: `id, title, description, director , producer, release data and rt_score`

### List your favorites

You should list all your favorites in json format. `GET http://localhost:8080/favorite`

### Pick a single favorite

You should get a single movie `GET http://localhost:8080/favorite/ID_OF_MOVIE`

### Remove a single favorite

You should remove a favorite `DELETE http://localhost:8080/favorite/ID_OF_MOVIE`

### Retrive some statistique

You should retrieve all species(name,gender and age) with brown eyes between 2006 and 2013 where the movie rate is superior at 85 `GET http://localhost:8080/stats` as json.

### Test

Every endpoint must be test.

## Helper

You could use `docker-compose.yml` to setup a fresh mysql database or use you own. (don't forget to set the credential on the property file)

```bash
$ docker-compose up
```



