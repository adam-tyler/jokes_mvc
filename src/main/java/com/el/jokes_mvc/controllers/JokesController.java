package com.el.jokes_mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.el.jokes_mvc.models.Joke;
import com.el.jokes_mvc.repositories.JokeRepository;

@Controller
public class JokesController {
	
	@Autowired
	private JokeRepository jokeRepository;

	public JokesController() {
		
	}
	
	@GetMapping("/")
	public ModelAndView calculator() {
		ModelAndView mv = new ModelAndView();
		List<Joke> jokes = jokeRepository.findAll();
		mv.addObject("jokes", jokes);
		mv.setViewName("read");
		return mv;
	}
	
	@GetMapping("/create")
	public ModelAndView getCreate() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("create");
		return mv;
	}
	
	@PostMapping("/create")
	public RedirectView postCreate(String joke, String punchline, int rating) {
		Joke jokeObject = new Joke();
		jokeObject.setJoke(joke);
		jokeObject.setPunchline(punchline);
		jokeObject.setRating(rating);
		jokeRepository.save(jokeObject);
		return new RedirectView("/");
	}
	
	@GetMapping("/edit/{jokeId}")
	public ModelAndView getEdit(@PathVariable int jokeId) {
		Joke joke = jokeRepository.findOne(jokeId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("joke", joke);
		mv.setViewName("edit");
		
		return mv;
	}
	
	@PostMapping("/edit/{jokeId}")
	public RedirectView putEdit(@PathVariable int jokeId, String joke, String punchline, int rating) {
		Joke jokeObject = jokeRepository.findOne(jokeId);
		jokeObject.setJoke(joke);
		jokeObject.setPunchline(punchline);
		jokeObject.setRating(rating);
		jokeRepository.save(jokeObject);
		return new RedirectView("/");
	}
	
	@GetMapping("/delete/{jokeId}")
	public RedirectView delete(@PathVariable int jokeId) {
		jokeRepository.delete(jokeId);
		return new RedirectView("/");
	}
}
