package com.iwe.avengers;

import java.util.NoSuchElementException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.exception.AvengerNotFoundException;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.dao.AvengerDAO;

public class RemoveAvengerHandler implements RequestHandler<Avenger, HandlerResponse> {

	private AvengerDAO dao = AvengerDAO.getInstance();
	
	@Override
	public HandlerResponse handleRequest(final Avenger avenger, final Context context) {
		final String id = avenger.getId();
		context.getLogger().log("[#] - Search Avenger with id"+id);
		
		try {		
			final Avenger retrievedAvenger = dao.find(id);
			
			dao.delete(retrievedAvenger);
			context.getLogger().log("[#] - Avenger deleted =) ");
	
			final HandlerResponse response = HandlerResponse.builder().setStatusCode(200).build();
			return response;
			
		}catch (NoSuchElementException e) {
			throw new AvengerNotFoundException("[NotFound] - Avenger id:"+id+" not found");
		}
	}
}
