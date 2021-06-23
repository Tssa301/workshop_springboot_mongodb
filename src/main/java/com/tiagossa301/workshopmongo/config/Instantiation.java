package com.tiagossa301.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.tiagossa301.workshopmongo.domain.Post;
import com.tiagossa301.workshopmongo.domain.User;
import com.tiagossa301.workshopmongo.dto.AuthorDTO;
import com.tiagossa301.workshopmongo.dto.CommentDTO;
import com.tiagossa301.workshopmongo.repository.PostRepository;
import com.tiagossa301.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown","maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("20/06/2021"), "Let's travel!", "I will travel to Brazil. See you later!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("22/06/2021"), "Good morning!", "I'm so happy today ;)", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Have a good trip!", sdf.parse("20/06/2021"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Enjoy it!", sdf.parse("21/06/2021"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Have a great day!", sdf.parse("22/06/2021"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}
}
