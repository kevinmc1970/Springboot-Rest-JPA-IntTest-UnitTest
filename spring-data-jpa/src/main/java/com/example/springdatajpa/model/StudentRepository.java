package com.example.springdatajpa.model;

import org.springframework.data.repository.CrudRepository;

// the cool thing about spring data JPA is that it uses convention to say that ALL repositories will usually do an create,read,update and delete
// on the entity so it provides that out-of-the-box. We just need to extend it to add anything bespoke to our app like findByDescription etc
public interface StudentRepository extends CrudRepository<Student, String> {

}