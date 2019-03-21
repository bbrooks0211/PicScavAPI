<p align="center">
  <img src="http://www.brendanbrooks.net/wp-content/uploads/2019/03/picscav-logo-main-400.png">
</p>

# PicScavAPI
Server-side API for PicScav (Not yet on the App Store). Code for iOS not yet ready to be made public.

Created by Brendan Brooks ([Website](http://www.brendanbrooks.net/))

# Overview
At a very high level, this is the code for the server of the PicScav iOS App, represented by the Java Spring section below:

![alt text](http://www.brendanbrooks.net/wp-content/uploads/2019/03/LogicalSolution.png "Architecture")

## Technologies Used In This Project
### Java
* Spring Framework
* JDBC
* Connection Pooling
* Spring Security
* JAX-RS
* Maven
* Eclipse for EE Developers
### Swift
* Xcode
### AWS
* Elastic Beanstalk
* S3
* CodeDeploy
* CodeBuild
* CodePipeline
* Route 53
* RDS
* Certificate Manager

# Installation
Though this is not intended to be open-source, it still may be useful for someone. It should be noted that this software is protected under GPLv3. 

A database fitting the proper specs and a custom app.properties file with the following is required:
```
db.url = jdbc:mysql://url.com/PicScav
db.user = user
db.pass = password
aws.cred1 = token1
aws.cred2 = token2
basic.user = http_basic_username
basic.pass = http_basic_password
```

## Database Diagram
![alt text](http://www.brendanbrooks.net/wp-content/uploads/2019/03/ER-Diagram.png "Database")

# More info
More info, including the full design spec, can be found [here](http://www.brendanbrooks.net/picscav/)
