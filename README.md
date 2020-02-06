# Trending GitHub Repo

This Android project is built with MVVM architecture using [Android Jetpack components](https://developer.android.com/jetpack) 

## Package-Structure:

This Project contains mainly four components :

	
        1. DataSource - This package provides data source to the App using both Network and Room DB.
	
        2. DI - This package contains Dagger v2.0 Modules and Components used to provide dependency at runtime.
	
        3. Repository - This layer provides an interface between ViewModel and DataSource.
	
        4. View/ViewModel - This layer provides interaction between View and ViewModel using Data Binding approach.

## Dependencies:

To build this App following third party dependencies have been used:

		1. Mockito: Use to Mock the Instances
	
		2. Glide: Use to load images inside Recycler View
	
		3. Retrofit and Moshi: Use to make rest Api call and Parse Json to Model
		
		4. Dagger: Use to Inject dependency of various components

## Notes :

		1. Progress-Dialog has been used instead of Shimmer Layout.
	
		2. Handles basic Test case using JUnit and Mockito.
		
##### About DEV :
[Linkedin](https://www.linkedin.com/in/pardeep-sharma-dev/)

