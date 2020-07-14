package com.eghm.service;

import com.eghm.service.model.Course;
import com.eghm.service.model.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ExecutableFindOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 二哥很猛
 */
@SpringBootApplication
public class ServiceMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceMongoApplication.class, args);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() {
		insert(3);
		insert(3);
		//get(3);
		// remove(3);
		query(3);
	}

	private void query(int id) {
		List<User> list = mongoTemplate.query(User.class).inCollection("User").as(User.class)
				.matching(Criteria.where("id").is(id)).all();
		System.out.println("*********** query ****************\n" + list.get(0));
	}

	private void remove(int id) {
		DeleteResult result = mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), User.class,"User");
		System.out.println(result.getDeletedCount());
	}
	private void get(int id) {
		Query query = Query.query(Criteria.where("id").is(id));
		List<User> user = mongoTemplate.find(query, User.class, "User");
		System.out.println(user.get(0));
	}

	private void insert(int id){
		User user = new User();
		user.setId(id);
		user.setName("二哥很猛");
		user.setAge(29);
		user.setSex(1);
		Course course = new Course();
		course.setCourseName("affairt");
		course.setScore(20);
		List<Course> courseList = new ArrayList<>();
		courseList.add(course);
		Course english = new Course();
		english.setCourseName("english");
		english.setScore(50);
		courseList.add(english);
		user.setCourseList(courseList);
		mongoTemplate.save(user);
	}

}
