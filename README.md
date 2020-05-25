# NvgTest
 **Section B**
1. We assume that Favorites test cases will base on existed system:  
	a. Anonymous user can uses Favorites feature.  
	b. After login, Favorites will add to list Favorites of the account which is logged-in.  
	c. After logout, all of favorites of anonymous account will be clear.  
	d. Maximum item favorites in one page is 20.


 **Section C**
1. How to run:  
  a. Import project as a Maven project.  
  b. Edit test data in the file "testdata.txt".  
  c. Run this project.  
  d. The test result will print in console.  

2. Tester must edit test data to run this project: "username" + "password" of amazon's account.
3. Assume that we disabled 2 step verification from Amazon.
4. We should apply testNg or JUnit to run the test.