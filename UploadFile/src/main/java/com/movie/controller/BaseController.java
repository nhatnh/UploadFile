package com.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.movie.common.SystemConstant;


@Controller
@RequestMapping("/")
public class BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String getIndexPage() {
		return SystemConstant.VIEW_MOVIE_RECORD;
	}

}