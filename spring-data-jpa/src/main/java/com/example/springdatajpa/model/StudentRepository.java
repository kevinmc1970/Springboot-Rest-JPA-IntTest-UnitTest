package com.example.springdatajpa.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

// the cool thing about spring data JPA is that it uses convention to say that ALL repositories will usually do an create,read,update and delete
// on the entity so it provides that out-of-the-box. We just need to extend it to add anything bespoke to our app like findByDescription etc
// don't need to implement interface either - done automatically in runtime
public interface StudentRepository extends CrudRepository<Student, String> {

//	@Query(
//			value="select * from Student where description = :description",
//			nativeQuery=true)	
//	List<Student> findByDescription(String description);

	// actually dont need to do above if simple query - SB JPA will work it out by the method name matched to the Entity
	// Student entity has a field called description and it's a String
	// JPA 'knows' what to do with 'findBy'
	List<Student> findByDescription(String description);
	
	// another way is used a NamedQuery in the Entity itself (would be like Query example with sql above) and would just require method here
	//	List<Student> findByDescription(String description);	
}
