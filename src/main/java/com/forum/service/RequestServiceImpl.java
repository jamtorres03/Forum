package com.forum.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.forum.model.Request;
import com.forum.repository.RequestRepository;

@Service("requestService")
public class RequestServiceImpl implements RequestService {

	@Value("${REQUEST.STATUS.NEW}")
	private static String REQUEST_STATUS_NEW;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Override
	public void saveRequest(Request request) {
		request.setStatus(1);
		request.setRequestDate(new Date());
		requestRepository.save(request);
	}

}
