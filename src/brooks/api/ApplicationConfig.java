package brooks.api;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import brooks.api.business.CategoryBusinessService;
import brooks.api.business.FriendsBusinessService;
import brooks.api.business.GameBusinessService;
import brooks.api.business.GameInvitesBusinessService;
import brooks.api.business.GameItemsBusinessService;
import brooks.api.business.GamePlayerBusinessService;
import brooks.api.business.ItemReferenceBusinessService;
import brooks.api.business.UserBusinessService;
import brooks.api.business.interfaces.CategoryServiceInterface;
import brooks.api.business.interfaces.FriendsBusinessServiceInterface;
import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.business.interfaces.GameInvitesBusinessServiceInterface;
import brooks.api.business.interfaces.GameItemsServiceInterface;
import brooks.api.business.interfaces.GamePlayerInterface;
import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.controllers.TestController;
import brooks.api.data.CategoryDAO;
import brooks.api.data.FoundItemDAO;
import brooks.api.data.FriendDAO;
import brooks.api.data.FriendInviteDAO;
import brooks.api.data.GameDAO;
import brooks.api.data.GameInviteDAO;
import brooks.api.data.GameItemDAO;
import brooks.api.data.GamePlayerDAO;
import brooks.api.data.ItemReferenceDAO;
import brooks.api.data.UserDAO;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.GameDAOInterface;
import brooks.api.data.interfaces.UserDataAccessInterface;
import brooks.api.models.CategoryModel;
import brooks.api.models.FoundItemModel;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;
import brooks.api.models.GameInviteModel;
import brooks.api.models.GameItemModel;
import brooks.api.models.GameModel;
import brooks.api.models.ItemModel;
import brooks.api.models.PlayerModel;
import brooks.api.utility.S3Utility;
import brooks.api.utility.interceptors.LoggingInterceptor;
import brooks.api.utility.interfaces.s3UtilityInterface;

@Configuration
@PropertySource("classpath:app.properties")
public class ApplicationConfig {
	
	@Value("${db.url}") String dbUrl;
	@Value("${db.user}") String dbUser;
	@Value("${db.pass}") String dbPass;
	@Value("${aws.cred1}") String awsCred1;
	@Value("${aws.cred2}") String awsCred2;
	
	@Bean(name="testController")
	public TestController getHelloController()
	{
		return new TestController();
	}
	
	@Bean(name="userDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public UserDataAccessInterface getUserDAO()
	{
		
		return new UserDAO();
	}
	
	@Bean(name="userBusinessService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public UserBusinessServiceInterface getUserBusinessService() {
		return new UserBusinessService();
	}
	
	@Bean(name="friendDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<FriendModel> getFriendDAO() {
		return new FriendDAO();
	}
	
	@Bean(name="friendInviteDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<FriendInviteModel> getFriendInviteDAO() {
		return new FriendInviteDAO();
	}
	
	@Bean(name="friendsBusinessService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public FriendsBusinessServiceInterface getFriendsBusinessService() {
		return new FriendsBusinessService();
	}
	
	@Bean(name="gameDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public GameDAOInterface getGameDAO() {
		return new GameDAO();
	}
	
	@Bean(name="gameService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public GameBusinessServiceInterface getGameBusinessService() {
		return new GameBusinessService();
	}
	
	@Bean(name="gameInviteDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<GameInviteModel> getGameInviteDAO() {
		return new GameInviteDAO();
	}
	
	@Bean(name="s3Utility")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public s3UtilityInterface getS3UtilityInterface() {
		return new S3Utility();
	}
	
	@Bean(name="gameInvitesService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public GameInvitesBusinessServiceInterface gameInvitesService() {
		return new GameInvitesBusinessService();
	}
	
	@Bean(name="playerDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<PlayerModel> getPlayerDAO() {
		return new GamePlayerDAO();
	}
	
	@Bean(name="itemDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<ItemModel> getItemDAO() {
		return new ItemReferenceDAO();
	}
	
	@Bean(name="itemReferenceService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public ItemReferenceBusinessServiceInterface getItemService() {
		return new ItemReferenceBusinessService();
	}
	
	@Bean(name="gameItemDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<GameItemModel> getGameItemDAO() {
		return new GameItemDAO();
	}
	
	@Bean(name="gameItemsService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public GameItemsServiceInterface getGameItemService() {
		return new GameItemsBusinessService();
	}
	
	@Bean(name="categoryDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)

	public DataAccessInterface<CategoryModel> getCategoryDAO() {
		return new CategoryDAO();
	}
	
	@Bean(name="categoryService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public CategoryServiceInterface getCategoryService() {
		return new CategoryBusinessService();
	}
	
	@Bean(name="playerService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public GamePlayerInterface getPlayerBusinessService() {
		return new GamePlayerBusinessService();
	}
	
	@Bean(name="foundItemDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataAccessInterface<FoundItemModel> getFoundItemDAO() {
		return new FoundItemDAO();
	}
	
	@Bean(name="s3Client")
	public AmazonS3 s3Client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsCred1, awsCred2);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName("us-west-1")).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		return s3Client;
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() { 
	    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	    commonsMultipartResolver.setDefaultEncoding("utf-8");
	    commonsMultipartResolver.setMaxUploadSize(50000000);
	    return commonsMultipartResolver;
	}
	
	/**
	* Getter for DataSource SpringBean (singleton scoped)
	* @return DataSource
	*/
	@Bean(name="dataSource", destroyMethod = "close")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataSource getDataSource()
	{
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPass); 
		dataSource.setInitialSize(6);
		dataSource.setTestOnBorrow(true);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setValidationInterval(30000);
		return dataSource;
	}
}

/*
Copyright 2019, Brendan Brooks.  

This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/
