This is a fork from hello-slick-3.1 that uses Postgres.

It uses Java 1.8 and Docker machine and bash scripts.

Make sure to change the ip number in `postgres.url` in `src/main/resources/application.conf` to the value returned by `docker-machine ip default`.

Run `startDb.sh` to start or reset the Postgres db.

You can find the slides under 'releases'.

