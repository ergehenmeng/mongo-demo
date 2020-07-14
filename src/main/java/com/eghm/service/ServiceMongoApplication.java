package com.eghm.service;

import com.eghm.service.model.Course;
import com.eghm.service.model.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
		// insert(3);
		// get(3);
		// remove(3);
		// query(3);
		// upsert();
		// updateList();
		countScore();
	}
	private void countScore() {
		AggregationResults<User> aggregate = mongoTemplate.aggregate(Aggregation.newAggregation(
				Aggregation.match(Criteria.where("_id").is(3)),
				Aggregation.unwind("courseList"),
				Aggregation.group("_id").sum("courseList.score").as("sum")
		), "User", User.class);
		List<User> list = aggregate.getMappedResults();
		System.out.println(list);
		List<User> users = mongoTemplate.find(Query.query(Criteria.where("id").is(3)), User.class, "User");
		System.out.println(users);
	}





	private void updateList() {
		Update update = new Update();
		Course course = new Course();
		// 只要不是完全一样,就会以新增方式添加,如果一样,不做任何操作
		course.setCourseName("san");
		course.setScore(50);
		update.addToSet("courseList", course);
		mongoTemplate.upsert(Query.query(Criteria.where("id").is(3)), update, User.class, "User");
	}


	private void upsert(){
		Update update = Update.update("name","三哥核能").set("age",30);
		mongoTemplate.upsert(Query.query(Criteria.where("id").is(3)),update,User.class, "User");
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
