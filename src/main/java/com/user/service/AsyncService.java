package com.user.service;

import java.util.concurrent.Future;

public interface AsyncService {
	
	 public Future<String> doTaskOne() throws Exception;
	 
	 public Future<String> doTaskTwo() throws Exception;
	 
	 public Future<String> doTaskThree() throws Exception;
	 
}
