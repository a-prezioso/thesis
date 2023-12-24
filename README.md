To start the application you can simply use dockerfile in the root folder of the application.

Simply run the following commands:
1. Build the docker image
```
docker build -t thesis-application .
```
2. Run the docker image
```
docker run -p 8080:8080 thesis-application
```

After that you'll be able to access the application on localhost:8080

To trigger the entrpoints and check how the searches works you can import on postman the .json file that you can find in the 'postman' folder.

You will find the following endpoints:
- /search/spark-single-search
- /search/spark-multiple-search
- /search/no-spark-single-search
- /search/no-spark-multiple-search

There's no need to pass any parameter to the endpoints, the application will use the default values.

The application will start to find and print parameters over 20.000 files (that are in the 'data' folder) and finally print the results in milliseconds of the research.
You'll notice that for the single search, the 'no-spark' version is faster than the 'spark' version, but for the multiple search the 'spark' version is faster than the 'no-spark' version.
