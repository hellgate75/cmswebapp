# cmswebapp

A barebones Java app, which can easily be deployed to Heroku.

This application realize a demonstration small CMS that handle some information about some entity called Store, these entity are saved in a postgres database and cached during the search phase. It is possibe to view the Welcome Home Page, insert Stores, view and filter (by Name or Type) the saved stores. It is apply to concept such as responsiveness and adaptiveness. It is powered by Jax-Rs Rest Service to the database and the UI has been written in JavaServerPage 2.1 technology using the jquery and Boostrap javascript frameworks. This is a simulation regarding the Ralph Lauren brand.

[![How-To Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Running Locally

Make sure you have Java, PostGress and Maven installed.  Also, install the [Heroku Toolbelt](https://toolbelt.heroku.com/).

```sh
$ git clone https://github.com/heroku/crmwebapp.git
$ cd crmwebapp
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:8080](http://localhost:8080/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
DATABASE_URL=postgres://localhost:5432/java_database_name
```
Javadoc definition only available using :
```sh
$ mvn javadoc:jar
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
